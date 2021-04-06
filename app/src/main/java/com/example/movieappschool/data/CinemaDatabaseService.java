package com.example.movieappschool.data;

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

    public boolean doesUserExist(String user, String password) {
        String query = "SELECT Username, Password FROM User WHERE Username = '" + user + "' AND Password = '" + password + "'";
        boolean result = false;

        try {
            connect();
            executeQuery(query);

            if (resultSet.next()) {
                result = true;
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
