package com.example.movieappschool.domain;

public class User {

    private String mFirstName, mLastName, mUsername, mAddress, mEmail, mPassword, mDateBirth;
    private int mUserId;

    public User(int mUserId, String mFirstName, String mLastName, String mUsername, String mAddress, String mEmail, String mPassword, String mDateBirth) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mUsername = mUsername;
        this.mAddress = mAddress;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mUserId = mUserId;
        this.mDateBirth = mDateBirth;          
    }

    public int getUserId() {
        return mUserId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getDateBirth() {
        return mDateBirth;
    }
}
