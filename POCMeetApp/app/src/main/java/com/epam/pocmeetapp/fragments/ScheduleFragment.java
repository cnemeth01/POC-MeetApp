package com.epam.pocmeetapp.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.epam.pocmeetapp.Parse.ParseHelper;
import com.epam.pocmeetapp.R;
import com.epam.pocmeetapp.activities.MainScheduleActivity;
import com.epam.pocmeetapp.adapters.MyListAdapter;
import com.epam.pocmeetapp.pojos.MeetUp;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Csaba_Bela_Nemeth on 10/2/2014.
 */
public class ScheduleFragment extends Fragment {

    public String[] scheduleTitles;
    public String[] scheduleTimes;
    public int[] scheduleFavoriteBreaks;

    private MyListAdapter myListAdapter;

    private int track;
    private boolean isFavoriteBreakTalk;

    private TextView trackDescriptionTextView;
    private TextView trackDescriptionHeaderTextView;
    private LinearLayout talkLayout;
    private int scheduleType;
    private int color;
    private String meetUpDescription;
    private ListView listView;
    private List<MeetUp> meetUps;
    private List<MeetUp> selectedMeetUps;
    private String[] message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.track_schedule_layout, container,
                false);

        talkLayout = (LinearLayout) v.findViewById(R.id.color_block);

        listView = (ListView) v.findViewById(R.id.talk_list_view);
        trackDescriptionTextView = ((TextView) v
                .findViewById(R.id.track_description));


        color = Color.rgb(46, 69, 81);
        updateView(color);

        return v;
    }

    public void updateView(int scheduleType) {
        meetUps = new ArrayList<MeetUp>();
        selectedMeetUps = new ArrayList<MeetUp>();
        meetUps = ParseHelper.meetUpList;

        switch (scheduleType) {

            case 0:
                meetUpDescription = "You can listen to expert lecturers themes on every mobile platforms and computer sciens.";
                color = Color.rgb(11, 44, 70);
                for (MeetUp meetUp : meetUps) {
                    if (meetUp.getStart().compareTo(new Date(System.currentTimeMillis())) > 0) {
                        selectedMeetUps.add(meetUp);
                    }
                }
                break;

            case 1:
                meetUpDescription = "It contains the most interesting presentations on Android platforms.";
                color = Color.rgb(22, 44, 40);
                for (MeetUp meetUp : meetUps) {
                    if (meetUp.getStart().compareTo(new Date(System.currentTimeMillis())) < 0 && meetUp.getMeetTheme().equals("Android")) {
                        selectedMeetUps.add(meetUp);
                    }
                }
                break;

            case 2:
                meetUpDescription = "It contains the most interesting presentations on iOS platforms.";
                color = Color.rgb(31, 54, 11);
                for (MeetUp meetUp : meetUps) {
                    if (meetUp.getStart().compareTo(new Date(System.currentTimeMillis())) < 0 && meetUp.getMeetTheme().equals("iOS")) {
                        selectedMeetUps.add(meetUp);
                    }
                }
                break;

            case 3:
                meetUpDescription = "It contains the most interesting presentations on computer sciens .";
                color = Color.rgb(50, 10, 20);
                for (MeetUp meetUp : meetUps) {
                    if (meetUp.getStart().compareTo(new Date(System.currentTimeMillis())) < 0) {
                        selectedMeetUps.add(meetUp);
                    }
                }
                break;
            default:
                color = Color.rgb(46, 69, 81);
                break;
        }


        myListAdapter = new MyListAdapter(getActivity(), selectedMeetUps);
        listView.setAdapter(myListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //           This query find all Comments objects which are connected to a concrete MeetUp

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Comments");
                query.whereEqualTo("parent", selectedMeetUps.get(position));
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                        message = new String[parseObjects.size()];
                        int i = 0;
                        for (ParseObject object : parseObjects) {
                            message[i] = (object.getString("comment"));
                            i++;
                        }
                        createCommenmtDialog(position);

                    }
                });
            }
        });

        if (trackDescriptionTextView != null) {
            trackDescriptionTextView.setText(meetUpDescription);
            trackDescriptionTextView.setVisibility(View.VISIBLE);
        }

        if (talkLayout != null) {
            Log.d("Color", "color: " + color);
            talkLayout.setBackgroundColor(color);

        }
        myListAdapter.notifyDataSetChanged();
    }

    public void createCommenmtDialog(final int position) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Comments");
        alert.setItems(message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

// Set an EditText view to get user input
        final EditText input = new EditText(getActivity());
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String comment = input.getText().toString();

// this can add a comment object to an existing meetup

                ParseObject myComment = new ParseObject("Comments");
                myComment.put("comment", comment);
                myComment.put("parent", ParseObject.createWithoutData("MeetUp", selectedMeetUps.get(position).getObjectId()));
                myComment.saveInBackground();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        trackDescriptionTextView = null;
        talkLayout = null;
        trackDescriptionHeaderTextView = null;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateView(scheduleType);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            scheduleType = getArguments().getInt(MainScheduleActivity.SCHEDULE_TYPE);
            Log.d(ScheduleFragment.class.getName(), " on Resume, schedule type: " + scheduleType);
        }
    }
}
