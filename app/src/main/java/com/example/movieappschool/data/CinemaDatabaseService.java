package com.example.movieappschool.data;

import android.util.Log;

import com.example.movieappschool.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CinemaDatabaseService {
    private final String connectionUrl = "jdbc:jtds:sqlserver://aei-sql2.avans.nl:1443/CinemaApplicationDB";
    private final String user = "MovieB2";
    private final String password = "AnikaWante";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public CinemaDatabaseService() {

    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(connectionUrl, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    private void executeQuery(String query) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch(SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    private void disconnect() {
        try {
            connection.close();
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public List<Integer> getAllMovieIds() {
        String query = "SELECT * FROM Movie";
        List<Integer> result = new ArrayList<>();

        try {
            connect();
            executeQuery(query);

            while (resultSet.next()) {
                result.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            disconnect();
        }

        return result;
    }

    public User doesUserExist(String user, String password) {
        String query = "SELECT * FROM Account WHERE username = '" + user + "' AND password = '" + password + "'";
        Log.d("USER EXIST", query);
        User result = null;

        try {
            connect();
            executeQuery(query);

            while (resultSet.next()) {
                int mUserId = resultSet.getInt("userID");
                String mFirstName = resultSet.getString("firstName");
                String mLastName = resultSet.getString("lastName");
                String mAddress = resultSet.getString("address");
                String mEmail = resultSet.getString("email");
                String mDateOfBirth = resultSet.getString("dateOfBirth");
                result = new User(mUserId, mFirstName, mLastName, user, mAddress, mEmail, password, mDateOfBirth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            disconnect();
        }

        return result;
    }
}