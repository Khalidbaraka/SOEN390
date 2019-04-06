package de.danoeh.antennapod.model;

public class User {

    private String fullName, email;

    //Public constructor
    public User(){

    }
    public User(String email, String fullName){
        this.email = email;
        this.fullName = fullName;

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
}
