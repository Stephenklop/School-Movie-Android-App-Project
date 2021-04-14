package com.example.movieappschool.data;

import android.util.Log;

import com.example.movieappschool.domain.Seat;
import com.example.movieappschool.domain.Show;
import com.example.movieappschool.domain.Ticket;
import com.example.movieappschool.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CinemaDatabaseService {
    private final String connectionUrl = "jdbc:jtds:sqlserver://aei-sql2.avans.nl:1443/CinemaApplicationDB";
    private final String user = "MovieB2";
    private final String password = "AnikaWante";
    private Connection connection;
    private Statement statement;
    private Stack<ResultSet> resultSet;

    public CinemaDatabaseService() {
        resultSet = new Stack<>();
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
            resultSet.push(statement.executeQuery(query));
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    private void disconnect() {
        try {
            if (resultSet.empty()) {
                connection.close();
            } else if (resultSet.peek() == null) {
                resultSet.pop();
                connection.close();
            } else {
                resultSet.pop().close();
            }

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

            ResultSet rs = resultSet.peek();

            while (rs.next()) {
                result.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            disconnect();
        }

        return result;
    }

    public List<Show> getAllShowsOfMovie(int movieId) {
        String query = "SELECT * FROM Show WHERE movieID = " + movieId + " ORDER BY dateTime";
        List<Show> result = new ArrayList<>();

        try {
            connect();
            executeQuery(query);

            ResultSet rs = resultSet.peek();

            while (rs.next()) {
                result.add(new Show(rs.getInt(2), rs.getString(4), rs.getInt(1), rs.getInt(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return result;
    }

    public boolean doShowsExist(int movieId) {
        String query = "SELECT TOP 1 * FROM Show WHERE movieID = " + movieId;
        boolean result = false;

        try {
            connect();
            executeQuery(query);

            ResultSet rs = resultSet.peek();

            while (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return result;
    }

    public List<Seat> getOccupiedSeats(int showId) {
        String query = "SELECT chairNr, rowNr FROM Ticket WHERE showId = " + showId;
        List<Seat> result = new ArrayList<>();

        try {
            connect();
            executeQuery(query);

            ResultSet rs = resultSet.peek();

            while (rs.next()) {
                result.add(new Seat(rs.getInt(1), rs.getInt(2)));
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

            ResultSet rs = resultSet.peek();

            while (rs.next()) {
                int mUserId = rs.getInt("userID");
                String mFirstName = rs.getString("firstName");
                String mLastName = rs.getString("lastName");
                String mAddress = rs.getString("address");
                String mEmail = rs.getString("email");
                String mDateOfBirth = rs.getString("dateOfBirth");
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
                "', ' " + lastname + "', '" + password + "', '" + email + "', '" + address + "', '" + datebirth + "')";

        try {
            connect();
            executeQuery(query);
        } finally {
            disconnect();
        }
    }

    public void updateUser(int mUserId, String mFirstName, String mLastName, String mUsername, String mAddress, String mEmail, String mPassword, String mDateBirth) {
        String query = "UPDATE Account SET username='" + mUsername + "', email='" + mEmail + "', password='" + mPassword + "', firstName='" + mFirstName
                + "', lastName='" + mLastName + "', dateOfBirth='" + mDateBirth + "', address='" + mAddress + "' WHERE userID='" + mUserId + "'";

        try {
            connect();
            executeQuery(query);
        } finally {
            disconnect();
        }
    }

    public void changePassword(String mUsername, String mOldPassword, String mNewPassword) {
        String query = "Update Account SET password = '" + mNewPassword + "' WHERE username = '" + mUsername + "' AND password = '" + mOldPassword + "'";
        try {
            connect();
            executeQuery(query);
        } finally {
            disconnect();
        }
    }

    public List<Ticket> getTicketList(int userId) {
        List<Ticket> ticketList = new ArrayList<>();

        String query = "SELECT * FROM Ticket WHERE userID = '" + userId + "'";

        try {
            connect();
            executeQuery(query);

            ResultSet rs = resultSet.peek();

            while (rs.next()) {
                int ticketId = rs.getInt("ticketID");
                int uId = rs.getInt("userID");
                int seatNumber = rs.getInt("chairNr");
                int rowNumber = rs.getInt("rowNr");
                int showId = rs.getInt("showID");
                String ticketType = rs.getString("ticketType");
                double price = rs.getDouble("price");

                Ticket ticket = new Ticket(ticketId, uId, seatNumber, rowNumber, ticketType, price);
                ticket.setShow(getShow(showId));
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return ticketList;
    }

    public Show getShow(int showId) {
        String query = "SELECT * FROM Show WHERE showID = " + showId;
        Show result = null;

        try {
            connect();
            executeQuery(query);

            ResultSet rs = resultSet.peek();

            while (rs.next()) {
                int movieId = rs.getInt("movieID");
                String fullDate = rs.getString("dateTime");
                int id = rs.getInt("showID");
                int hallId = rs.getInt("hallNr");

                result = new Show(movieId, fullDate, id, hallId);
                result.setMovie(LocalAppStorage.getMovie(movieId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return result;
    }

    public void createTicket(int userId, int chairNr, int rowNr, int showId, String ticketType, double price) {
        String query = "INSERT INTO Ticket (userID, chairNr, rowNr, showID, ticketType, price) VALUES ('" + userId + "', '" + chairNr +
                "', ' " + rowNr + "', '" + showId + "', '" + ticketType + "', '" + price + "')";

        try {
            connect();
            executeQuery(query);
        } finally {
            disconnect();
        }
    }

    public void deleteExpiredTickets() {
        String query = "EXEC DeleteExpiredTickets;";

        try {
            connect();
            executeQuery(query);
        } finally {
            disconnect();
        }
    }

    public void deleteExpiredShows() {
        String query = "EXEC DeleteExpiredShows;";

        try {
            connect();
            executeQuery(query);
        } finally {
            disconnect();
        }
    }
}
