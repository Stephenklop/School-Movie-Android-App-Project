package com.example.movieappschool.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieappschool.R;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.Movie;

import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private LocalAppStorage localAppStorage;
    private List<Movie> movies;
    private Movie movie;
    private ImageView poster;
    private TextView title;
    private TextView releaseyear;
    private TextView genres;
    private TextView movieLength;
    private TextView rating;
    private int movieId;

    public DetailActivity() {
        localAppStorage = (LocalAppStorage) this.getApplication();
        movies = localAppStorage.getMovies();
    }

    public String getYearFromDate(String date) {
        return date.split("-")[0];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        poster = findViewById(R.id.detail_poster);
        title = findViewById(R.id.detail_title);
        releaseyear = findViewById(R.id.detail_releaseyear);
        genres = findViewById(R.id.detail_genres);
        movieLength = findViewById(R.id.detail_movie_length);

        // Get extra id from intent and store it locally.
        Intent intent = getIntent();
        movieId = intent.getIntExtra("id", -1);

        if (movieId >= 0) {
            // Load the corresponding movie.
            for (Movie movie : movies) {
                if (movie.getId() == movieId) {
                    this.movie = movie;
                }
            }

            // Add Poster
            Glide.with(this).load(movie.getPosterURL()).into(poster);

            // Set Title
            title.setText(movie.getTitle());

            // Set Release Year
            releaseyear.setText(getYearFromDate(movie.getReleaseDate()));

            // Set Genres
            for(String genre : movie.getGenreIds()) {
                genres.append(genre + "/");
            }
        } else {
            // Something went wrong, show error.
            // TODO: Handle error
            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_LONG).show();
        }
    }
}