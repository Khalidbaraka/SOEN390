package de.danoeh.antennapod.model;

import java.io.Serializable;

public class Comment implements Serializable {
    public String user;
    public String commentId;
    public String timestamp;
    public String comment;
    public String podcast;

    public Comment() {
    }


    public Comment(String user, String commentId, String timestamp, String comment, String podcast) {

        this.user = user;
        this.commentId = commentId;
        this.timestamp = timestamp;
        this.comment = comment;
        this.podcast=podcast;

    }

    public String getUser() {

        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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