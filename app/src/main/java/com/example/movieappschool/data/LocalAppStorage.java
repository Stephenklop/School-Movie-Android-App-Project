package com.example.movieappschool.data;

import android.app.Application;

import com.example.movieappschool.domain.Movie;
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