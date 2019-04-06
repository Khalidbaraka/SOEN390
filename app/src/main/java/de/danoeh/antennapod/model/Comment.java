package de.danoeh.antennapod.model;

import java.io.Serializable;

public class Comment implements Serializable {
    public String userid;
    public String useremail;
    public String timestamp;
    public String comment;
    public String podcast;

    //Do not remove default constructor
    public Comment() {
    }


    public Comment(String userid, String timestamp, String comment, String podcast, String useremail) {

        this.userid = userid;
        this.timestamp = timestamp;
        this.comment = comment;
        this.podcast=podcast;
        this.useremail = useremail;
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

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }
}