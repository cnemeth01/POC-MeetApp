package com.epam.pocmeetapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.epam.pocmeetapp.Parse.ParseHelper;
import com.epam.pocmeetapp.R;
import com.epam.pocmeetapp.adapters.MyListAdapter;
import com.google.android.gms.games.multiplayer.realtime.Room;

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

    private Room trackForTalk = null;

    private TextView trackDescriptionTextView;
    private TextView trackDescriptionHeaderTextView;
    private LinearLayout talkLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.track_schedule_layout, container,
                false);



        talkLayout = (LinearLayout) v.findViewById(R.id.color_block);

        ListView list = (ListView) v.findViewById(R.id.talk_list_view);

        myListAdapter=new MyListAdapter(getActivity(),ParseHelper.meetUpList);
        list.setAdapter(myListAdapter);

        return v;
    }

}
