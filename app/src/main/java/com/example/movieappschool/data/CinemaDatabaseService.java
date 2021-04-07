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

    public List<Integer> getOccupiedSeats(int showId) {
        String query = "SELECT chairNr FROM Ticket WHERE showId = '" + showId + "'";
        List<Integer> result = new ArrayList<>();

        try {
            connect();
            executeQuery(query);

            while (resultSet.next()) {
                result.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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


    public void createAccount(String username, String firstname, String lastname, String password, String email, String address, String datebirth) {
        String query = "INSERT INTO Account (username, firstName, lastName, password, email, address, dateOfBirth) VALUES ('" + username + "', '" + firstname +
                "', ' "+ lastname + "', '" + password + "', '" + email + "', '" + address + "', '" + datebirth + "')";

        try {
            connect();
            executeQuery(query);
        } finally {
            disconnect();
        }
    }
  
    public User updateUser(int mUserId, String mFirstName, String mLastName, String mUsername, String mAddress, String mEmail, String mPassword, String mDateBirth) {
        User user;
        user = doesUserExist(mUsername, mPassword);
        if (user != null) {
            return user;
            //TODO: Show error that the chosen username already exists
        }

        String query = "UPDATE Account SET username='" + mUsername + "', email='" + mEmail + "', password='" + mPassword + "', firstName='" + mFirstName
                + "', lastName='" + mLastName + "', dateOfBirth='" + mDateBirth + "', address='" + mAddress + "' WHERE userID='" + mUserId + "'";

        try {
            connect();
            executeQuery(query);

            while (resultSet.next()) {
                mUserId = resultSet.getInt("userID");
                mUsername = resultSet.getString("username");
                mFirstName = resultSet.getString("firstName");
                mLastName = resultSet.getString("lastName");
                mAddress = resultSet.getString("address");
                mEmail = resultSet.getString("email");
                mPassword = resultSet.getString("password");
                mDateBirth = resultSet.getString("dateOfBirth");
                user = new User(mUserId, mFirstName, mLastName, mUsername, mAddress, mEmail, mPassword, mDateBirth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return user;
    }
}
