package com.epam.pocmeetapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.epam.pocmeetapp.fragments.ItemFragment;
import com.epam.pocmeetapp.fragments.MeetAppsFragment;
import com.epam.pocmeetapp.pojos.MeetUp;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import java.util.ArrayList;
import java.util.List;


public class MeetAppsActivity extends Activity implements ItemFragment.OnFragmentInteractionListener, MeetAppsFragment.OnFragmentInteractionListener {

    public static final String ARSE_APPLICATION_ID = "al5f8PvpJpXuCjGbEQlVw4R5KCQXMDwPWypBJf5y";
    public static final String PARSE_CLIENT_KEY = "Qmw2h7VZ0URwAuBhGSn97oFFpfOv2I3krN866rxX";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_apps);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, MeetAppsFragment.newInstance("", ""))
                    .commit();
        }
        // Add your initialization code here
        Parse.initialize(this, ARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meet_apps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onFragmentInteraction() {




    }


    }

    /**
     * A placeholder fragment containing a simple view.
     */


