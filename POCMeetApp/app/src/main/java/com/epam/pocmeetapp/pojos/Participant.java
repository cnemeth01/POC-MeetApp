package com.epam.pocmeetapp.pojos;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Csaba_Bela_Nemeth on 10/8/2014.
 */
@ParseClassName("Participant")
public class Participant extends ParseObject {

    private String objectId;
    private String name;

    public String getObjectId() {
        return getString("objectId");
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name",name);
    }
}
