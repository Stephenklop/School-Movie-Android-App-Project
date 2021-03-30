package com.example.movieappschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.data.MovieDataService;
import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.ui.home.MovieAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieDataService movieDataService;
    private final String API_KEY = "b3dc30d1b882188c9c0161b97d66f032";
    private LocalAppStorage localAppStorage;
    private List<Movie> mMovies;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public MainActivity() {
        // Initialize MovieDataService for making API calls.
        movieDataService = new MovieDataService(API_KEY, "en-US", this);

        // Initialize LocalAppStorage for accessing the global storage.
        localAppStorage = (LocalAppStorage) this.getApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make a call to get the most popular movies using the MovieDataService.
        movieDataService.getPopularMovies(new MovieDataService.VolleyResponseListener() {
            @Override
            public void onResponse(Object response) {
                // Store the movies and set Adapter of RecyclerView when response is received.
                localAppStorage.setMovies((List<Movie>) response);
                mMovies = localAppStorage.getMovies();

                mAdapter = new MovieAdapter(mMovies, MainActivity.this);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onError(String message) {
                // Do something when an error occurred while trying to receive a response
                // TODO: Handle error
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        // RecyclerView
        recyclerView = findViewById(R.id.movie_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }
}