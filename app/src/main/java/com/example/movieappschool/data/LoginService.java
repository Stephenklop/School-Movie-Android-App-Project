package com.example.movieappschool.data;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginService extends AppCompatActivity {
    private CinemaDatabaseService cinemaDatabaseService;

    public LoginService() {
        cinemaDatabaseService = new CinemaDatabaseService("jdbc:jtds:sqlserver://aei-sql2.avans.nl:1443/CinemaApplicationDB", "MovieB2", "AnikaWante");
    }

    public void executeLogin(String Username, String Password) {

        // Check if both values are given
        if(checkIfNotEmpty(Username, Password)) {

            // Execute Query
            if(cinemaDatabaseService.doesUserExist(Username, Password)) {
                System.out.println("User is logged in");
            } else {
                System.out.println("User could not be logged in");
            }

            // Throw error when one of 2 values is empty
        } else {
            // TODO: Add error handling
        }
    }


    private boolean checkIfNotEmpty(String mUsernameInput, String mPasswordInput) {
        if(mUsernameInput.isEmpty() || mPasswordInput.isEmpty()) {
            return false;
        }
        return true;
    }
}
