package com.example.movieappschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.movieappschool.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAPITask.MovieListener {
    private final String API_KEY = BuildConfig.API_KEY;
    private List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] params = {"https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=en-US&page=1"};
        new MovieAPITask(this).execute(params);
    }

    @Override
    public void setMovies(List<Movie> movies) {
        // Assign List of movies to movies instance variable
        mMovies = new ArrayList<>(movies);
        Log.d("MainActivity", mMovies.toString());
    }
}