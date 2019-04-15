package de.danoeh.antennapod.model;

public class Reply {
   public String sender;
   public String senderName;
   public String senderEmail;
   public String receiverName;
   public String receiverEmail;
   public String commentID;
   public String reply;
   public String timestamp;
   public String podcastTitle;

    public Reply() {
    }

    public Reply(String sender,String senderName,String senderEmail,String receiverName, String receiverEmail ,String reply, String podcastTitle,String timestamp,String commentID) {
        this.sender = sender;
        this.senderName=senderName;
        this.senderEmail=senderEmail;
        this.receiverName=receiverName;
        this.receiverEmail=receiverEmail;
        this.reply = reply;
        this.podcastTitle = podcastTitle;
        this.timestamp=timestamp;
        this.commentID=commentID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getPodcastTitle() {
        return podcastTitle;
    }

    public void setPodcastTitle(String podcastTitle) {
        this.podcastTitle = podcastTitle;
    }
}
