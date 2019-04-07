package de.danoeh.antennapod.model;

public class User {

    private String fullName, email, imageURL;

    //Public constructor
    public User(){

    }

    public User(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public User(String email, String fullName, String imageURL){
        this.email = email;
        this.fullName = fullName;
        this.imageURL = imageURL;

    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}