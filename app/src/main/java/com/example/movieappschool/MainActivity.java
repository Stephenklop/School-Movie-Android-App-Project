package com.example.movieappschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.movieappschool.data.MovieDataService;
import com.example.movieappschool.domain.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private final String API_KEY = BuildConfig.API_KEY;
    private List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize MovieDataService for making API calls.
        MovieDataService movieDataService = new MovieDataService(API_KEY, "en-US", this);

        // Example: make a call to get the most popular movies using the MovieDataService
        movieDataService.getPopularMovies(new MovieDataService.VolleyResponseListener() {
            @Override
            public void onResponse(Object response) {
                // Do something when the response is received
                mMovies = (List<Movie>) response;
                Toast.makeText(MainActivity.this, "Response received!", Toast.LENGTH_LONG).show();
                Log.d(TAG, mMovies.toString());
            }

            @Override
            public void onError(String message) {
                // Do something when an error occurred while trying to receive a response
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}