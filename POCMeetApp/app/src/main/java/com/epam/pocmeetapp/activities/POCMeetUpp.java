package com.epam.pocmeetapp.activities;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Csaba_Bela_Nemeth on 9/30/2014.
 */
public class POCMeetUpp extends Application {
    public static final String ARSE_APPLICATION_ID = "al5f8PvpJpXuCjGbEQlVw4R5KCQXMDwPWypBJf5y";
    public static final String PARSE_CLIENT_KEY = "Qmw2h7VZ0URwAuBhGSn97oFFpfOv2I3krN866rxX";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, ARSE_APPLICATION_ID, PARSE_CLIENT_KEY);


    }
}
