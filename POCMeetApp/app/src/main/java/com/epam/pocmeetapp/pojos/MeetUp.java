package com.epam.pocmeetapp.pojos;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

/**
 * Created by Csaba_Bela_Nemeth on 9/29/2014.
 */
@ParseClassName("MeetUp")
public class MeetUp extends ParseObject {

    private String objectId;
    private String meetTheme;
    private String meetUpTitle;
    private Date start;
    private Date finish;
    private String speakerId;
    private List<String> participants;

    public String getObjectId() {
        return getString("objectId");
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

    public String getSpeakerId() {

        return getString("speakerId");
    }
    public List<String> getParticipannts() {

        return getList("participants");
    }

    public void addParticipant(String participantId){
        put("participants",participantId);
    }


}
