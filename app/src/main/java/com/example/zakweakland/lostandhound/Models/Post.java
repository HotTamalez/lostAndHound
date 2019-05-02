package com.example.zakweakland.lostandhound.Models;

import com.google.firebase.database.ServerValue;

import java.io.Serializable;

public class Post implements Serializable {

    private String postKey;
    private String breed;
    private String dogName;
    private String dogAge;
    private String additionalInfo;
    private String image;
    private String userID;
    private Object timestamp;

    public Post(String breed, String dogName, String dogAge, String additionalInfo, String image, String userID) {
        this.breed = breed;
        this.dogName = dogName;
        this.dogAge = dogAge;
        this.additionalInfo = additionalInfo;
        this.image = image;
        this.userID = userID;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Post(){

    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getBreed() {
        return breed;
    }

    public String getDogName() {
        return dogName;
    }

    public String getDogAge() {
        return dogAge;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getImage() {
        return image;
    }

    public String getUserID() {
        return userID;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public void setDogAge(String dogAge) {
        this.dogAge = dogAge;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

}


