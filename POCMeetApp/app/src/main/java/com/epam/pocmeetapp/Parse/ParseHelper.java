package com.epam.pocmeetapp.Parse;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.epam.pocmeetapp.activities.MainScheduleActivity;
import com.epam.pocmeetapp.interfaces.ParseCallBack;
import com.epam.pocmeetapp.pojos.MeetUp;
import com.epam.pocmeetapp.pojos.Participant;
import com.epam.pocmeetapp.pojos.Speakers;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Csaba_Bela_Nemeth on 10/3/2014.
 */
public class ParseHelper {

    private Context context;
    private ParseCallBack mListener;
    public static final List<MeetUp> meetUpList = new ArrayList<MeetUp>();
    public static final List<Participant> participantList = new ArrayList<Participant>();


    public ParseHelper(Context context,ParseCallBack mListener) {
        this.context = context;
        this.mListener= mListener;
    }



    public void getMeetUps() {

        ParseQuery<MeetUp> query = ParseQuery.getQuery(MeetUp.class);
        query.orderByAscending("Theme");
        query.findInBackground(new FindCallback<MeetUp>() {
            public void done(List<MeetUp> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    meetUpList.clear();
                    meetUpList.addAll(scoreList);

                    mListener.parseQueryDone();
                    //if (participantList.isEmpty()) {
                    //    getParticipiants();
                    //}
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void getParticipiants() {
        ParseQuery<Participant> query = ParseQuery.getQuery(Participant.class);
        query.orderByAscending("Theme");
        query.findInBackground(new FindCallback<Participant>() {
            public void done(List<Participant> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score", "participiants Retrieved  " + scoreList.size() + " scores");
                    participantList.clear();
                    participantList.addAll(scoreList);

                    addParticipiants();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


    }

    private void addParticipiants() {
        for (MeetUp meetUp : meetUpList) {
            //for (Participant participant : participantList) {
            //    meetUp.addParticipant(participant);
            //}

            meetUp.put("participants", participantList);
            meetUp.saveInBackground();
        }
    }

    public void initParse(Intent intent) {
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        ParseAnalytics.trackAppOpened(intent);
        PushService.setDefaultPushCallback(context, MainScheduleActivity.class);
        ParseObject.registerSubclass(MeetUp.class);
        ParseObject.registerSubclass(Participant.class);
        ParseObject.registerSubclass(Speakers.class);

    }


}
