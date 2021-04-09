package com.example.movieappschool.domain;

public class User {

    private String mFirstName, mLastName, mUsername, mAddress, mEmail, mPassword, mDateBirth;
    private int mUserId;

    public User(int mUserId, String mFirstName, String mLastName, String mUsername, String mAddress, String mEmail, String mPassword, String mDateBirth) {
        if (mFirstName != null) {
            this.mFirstName = mFirstName;
        } else {
            throw new IllegalArgumentException("Firstname is invalid");
        }

        if (mLastName != null) {
            this.mLastName = mLastName;
        } else {
            throw new IllegalArgumentException("Firstname is invalid");
        }
        // todo: check if username is already in use
        if (mUsername != null) {
            this.mUsername = mUsername;
        } else {
            throw new IllegalArgumentException("Username is invalid");
        }
        if (mAddress != null) {
            this.mAddress = mAddress;
        } else {
            throw new IllegalArgumentException("Username is invalid");
        }

        if (mEmail != null & mEmail.matches("^(.+)@(.+)$")) {
            this.mEmail = mEmail;
        } else {
            throw new IllegalArgumentException("email is invalid");
        }
        //todo: check if password is strong enough
        if (mPassword != null) {
            this.mPassword = mPassword;
        } else {
            throw new IllegalArgumentException("password is invalid");
        }

        if (mUserId > 0) {
            this.mUserId = mUserId;
        } else {
            throw new IllegalArgumentException("UserId is invalid");
        }
        //mDateBirth is allowed to be dd/mm/yyyy
        if (mDateBirth != null & mDateBirth.matches("(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)\n")) {
            this.mDateBirth = mDateBirth;
            throw new IllegalArgumentException("Datebirth is invalid");
        }
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
