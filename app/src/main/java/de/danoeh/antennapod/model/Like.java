package de.danoeh.antennapod.model;

public class Like {
    public String commentID;
    public String userID;

    public Like() {
        this.commentID = "";
        this.userID = "";
    }

    public Like(String commentID, String userID) {
        this.commentID = commentID;
        this.userID = userID;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
