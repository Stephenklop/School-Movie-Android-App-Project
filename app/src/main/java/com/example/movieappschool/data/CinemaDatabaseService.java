package com.example.movieappschool.data;

import android.util.Log;

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

public class CinemaDatabaseService {
    private final String connectionUrl = "jdbc:jtds:sqlserver://aei-sql2.avans.nl:1443/CinemaApplicationDB";
    private final String user = "MovieB2";
    private final String password = "AnikaWante";
    private Connection connection;
    private Statement statement;
    private final List<ResultSet> resultSets;
    private ResultSet resultSet;

    public CinemaDatabaseService() {
        resultSets = new ArrayList<>();
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
            resultSets.add(statement.executeQuery(query));
            resultSet = resultSets.get(resultSets.size() - 1);
        } catch(SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    private void disconnect() {
        try {
            if (resultSets.size() < 2) {
                connection.close();
                resultSet.close();
                resultSets.get(0).close();
            } else {
                int index = resultSets.size() - 1;

                resultSet.close();
                resultSet = resultSets.get(index - 1);

                resultSets.get(index).close();
                resultSets.remove(index);
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
        }
        finally {
            disconnect();
        }
    }
  
    public User updateUser(int mUserId, String mFirstName, String mLastName, String mUsername, String mAddress, String mEmail, String mPassword, String mDateBirth) {
        User user;
        user = doesUserExist(mUsername, mPassword);
        if (user != null) {
            return user;
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

    public void changePassword(String mUsername, String mOldPassword, String mNewPassword) {
        String query = "Update Account SET password = '" + mNewPassword + "' WHERE username = '" + mUsername + "' AND password = '" + mOldPassword + "'";
        try {
            connect();
            executeQuery(query);
        }
        finally {
            disconnect();
        }
    }

    public List<Ticket> getTicketList(int userId){
        List<Ticket> ticketList = new ArrayList<>();

        String query = "SELECT * FROM Ticket WHERE userID = '" + userId + "'";

        int mTicketId;
        int mUserId;
        int mSeatNumber;
        int mRowNumber;
        int mShowId;
        double mPrice;

        try {
            connect();
            executeQuery(query);

            while (resultSet.next()) {
                mTicketId = resultSet.getInt("ticketID");
                mUserId = resultSet.getInt("userID");
                mSeatNumber = resultSet.getInt("chairNr");
                mRowNumber = resultSet.getInt("rowNr");
                mShowId = resultSet.getInt("showID");
                // TODO: ADD PRICE TO TICKET
                //mPrice = resultSet.getDouble("price");
                Ticket ticket = new Ticket(mTicketId, mUserId, mSeatNumber, mRowNumber, 10);
                ticket.setShow(getShow(mTicketId));
                ticketList.add(ticket);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }

        return ticketList;
    }

    public Show getShow(int showId){
        String query = "SELECT * FROM Show WHERE showID = " + showId;
        Show result = null;

        try {
            connect();
            executeQuery(query);

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movieID");
                String fullDate = resultSet.getString("dateTime");
                int id = resultSet.getInt("showID");
                int hallId = resultSet.getInt("hallNr");

                result = new Show(movieId, fullDate, id, hallId);
                result.setMovie(LocalAppStorage.getMovie(movieId));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }

        return result;
    }
}
