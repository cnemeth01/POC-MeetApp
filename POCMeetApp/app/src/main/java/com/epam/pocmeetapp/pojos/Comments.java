package com.epam.pocmeetapp.pojos;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Németh Csaba on 2014.10.12..
 */

@ParseClassName("Comments")
public class Comments extends ParseObject {

    private String authorId;
    private String comment;

    public String getComment() {
        return getString("comment");
    }

    public void setComment(String comment) {
        put("comment",comment);
    }

    public String getAuthorId() {
        return getString("authorId");
    }

    public void setAuthor(String authorId) {
        put("authorId",authorId);
    }
}
