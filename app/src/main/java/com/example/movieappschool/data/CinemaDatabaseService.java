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

    private void connect() throws SQLException {
        connection = DriverManager.getConnection(connectionUrl, user, password);
    }

    private void executeQuery(String query) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
    }

    private void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
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

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
}
