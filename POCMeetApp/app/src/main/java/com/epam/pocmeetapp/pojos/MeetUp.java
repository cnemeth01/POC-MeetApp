package com.epam.pocmeetapp.pojos;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Csaba_Bela_Nemeth on 9/29/2014.
 */
@ParseClassName("MeetUp")
public class MeetUp extends ParseObject {

    private String MeetUpId;
    private String meetTheme;
    private String meetUpTitle;
    private Date start;
    private Date finish;
    private ParseFile speakerPicture;

    public String getMeetUpId() {
        return getString("objectId");
    }

    public void setMeetUpId(String meetUpId) {

        put("objectId", meetUpId);
    }

    public String getMeetTheme() {
        return getString("Theme");
    }

    public void setMeetTheme(String meetTheme) {
        put("Theme", meetTheme);
    }

    public String getMeetUpTitle() {
        return getString("Title");
    }

    public void setMeetUpTitle(String meetTheme) {
        put("Title", meetTheme);
    }

    public Date getStart() {

        return getDate("Start");
    }

    public void setStart(Date start) {
        put("Start", start);
    }

    public Date getFinish() {

        return getDate("Finish");
    }

    public void setFinish(Date finish) {

        put("Finish", finish);
    }

    public ParseFile getSpeakerPicture() {

        return getParseFile("Picture");
    }

    public void setSpeakerPicture(ParseFile speakerPicture) {

        put("Picture", speakerPicture);
    }
}
