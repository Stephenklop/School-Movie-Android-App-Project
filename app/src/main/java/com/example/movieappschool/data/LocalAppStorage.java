package com.example.movieappschool.data;

import android.app.Application;

import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.domain.Seat;
import com.example.movieappschool.domain.Show;
import com.example.movieappschool.domain.Ticket;
import com.example.movieappschool.domain.User;

import java.util.ArrayList;
import java.util.List;

// Globally accessible data storage class (might be replaced by SQLite later)
public class LocalAppStorage extends Application {
    private static boolean isLoggedIn;
    private static List<Movie> movies = new ArrayList<>();
    private static List<Ticket> tickets = new ArrayList<>();
    private static User user;

    public static void setMovies(List<Movie> movies) {
        LocalAppStorage.movies = movies;
    }

    public static void setTickets(List<Ticket> tickets) {
        LocalAppStorage.tickets = tickets;
    }

    public static List<Movie> getMovies() {
        return movies;
    }

    public static List<Ticket> getTickets() {
        return tickets;
    }

    //ticketList
    public static List<Ticket> ticketList = new ArrayList<Ticket>();

    public static List<Ticket> getTicketList() { return ticketList; }

    public static void setTicketList(List<Ticket> ticketList) { LocalAppStorage.ticketList = ticketList; }

    //seatList
    public static List<Seat> seatList = new ArrayList<Seat>();

    public static List<Seat> getSeatList() { return seatList; }

    public static void setSeatList(List<Seat> seatList) { LocalAppStorage.seatList = seatList; }

    //showList
    public static List<Show> showList = new ArrayList<Show>();

    public static List<Show> getShowList() { return showList; }

    public static void setShowList(List<Show> showList) { LocalAppStorage.showList = showList; }
  
    public static User getUser() {
        return user;
    }

    public static void setUser(User mUser) {
        user = mUser;
    }

    public static void deleteUser() {
        user = null;
    }

    public static void setLoggedIn() {
        isLoggedIn = true;
    }

    public static boolean getLoggedIn() {
        return isLoggedIn;
    }

    public static void setLoggedOut() { isLoggedIn = false; }

    public static Movie findMovie(int movieId) {
        Movie result = null;

        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);

            if (movie.getId() == movieId) {
                result = movie;
            }
        }

        return result;
    }
}