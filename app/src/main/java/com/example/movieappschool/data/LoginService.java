package com.example.movieappschool.data;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginService extends AppCompatActivity {

    private CinemaDatabaseService cinemaDatabaseService;
    private User result;

    public LoginService() {
        cinemaDatabaseService = new CinemaDatabaseService();
    }

    public User executeLogin(String Username, String Password) {


        // Check if both values are given
        if(checkIfNotEmpty(Username, Password)) {

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    result = cinemaDatabaseService.doesUserExist(Username, Password);
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Execute Query
                    if(result != null) {
                        System.out.println("User is logged in");
                    } else {
                        // TODO: Add error incorrect combination
                        System.out.println("User could not be logged in");
                    }
                }
            });

            try {
                t1.start();
                t1.join();

                t2.start();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Throw error when one of 2 values is empty
        } else {
            // TODO: Add error handling
        }
        return result;
    }


    private boolean checkIfNotEmpty(String mUsernameInput, String mPasswordInput) {
        if(mUsernameInput.isEmpty() || mPasswordInput.isEmpty()) {
            return false;
        }
        return true;
    }
}