package de.danoeh.antennapod.model;

import java.io.Serializable;

public class Comment implements Serializable {
    public String userid;
    public String timestamp;
    public String comment;
    public String podcast;

    public Comment() {
    }


    public Comment(String userid, String timestamp, String comment, String podcast) {

        this.userid = userid;
        this.timestamp = timestamp;
        this.comment = comment;
        this.podcast=podcast;

    }

    public String getUser() {

        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPodcast() {
        return podcast;
    }

    public void setPodcast(String podcast) {
        this.podcast = podcast;
    }
}