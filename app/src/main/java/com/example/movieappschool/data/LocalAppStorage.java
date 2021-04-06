package com.example.movieappschool.data;

import android.app.Application;

import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.domain.User;

import java.util.ArrayList;
import java.util.List;

// Globally accessible data storage class (might be replaced by SQLite later)
public class LocalAppStorage extends Application {
    private static List<Movie> movies = new ArrayList<>();
    private static List<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static void setMovies(List<Movie> movies) {
        LocalAppStorage.movies = movies;
    }

    public static List<Movie> getMovies() {
        return movies;
    }

    public static List<User> getUsers() {
        return users;
    }
}