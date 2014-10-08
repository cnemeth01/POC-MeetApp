package com.epam.pocmeetapp.pojos;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by Csaba_Bela_Nemeth on 10/8/2014.
 */
@ParseClassName("Speakers")
public class Speakers extends ParseObject {


    private String name;
    private String objectId;
    private ParseFile picture;


    public String getName() {
        return getString("name");
    }

    public String getObjectId() {
        return getString("objectId");
    }

    public ParseFile getPicture() {
        return getParseFile("picture");
    }


}
