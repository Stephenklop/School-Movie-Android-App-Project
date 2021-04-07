package com.example.movieappschool.data;

import android.app.Application;

import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.domain.Seat;
import com.example.movieappschool.domain.Show;
import com.example.movieappschool.domain.Ticket;

import java.util.ArrayList;
import java.util.List;

// Globally accessible data storage class (might be replaced by SQLite later)
public class LocalAppStorage extends Application {
    private static List<Movie> movies = new ArrayList<>();

    public static void setMovies(List<Movie> movies) {
        LocalAppStorage.movies = movies;
    }

    public static List<Movie> getMovies() {
        return movies;
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
}