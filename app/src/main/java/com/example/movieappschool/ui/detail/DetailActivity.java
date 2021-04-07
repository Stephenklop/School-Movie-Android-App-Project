package com.example.movieappschool.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieappschool.R;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.ui.home.MovieAdapter;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private LocalAppStorage localAppStorage;
    private List<Movie> movies;
    private Movie movie;
    private ImageView backgroundImage, poster, starOne, starTwo, starThree, starFour, starFive;
    private TextView title, releaseyear, genres, movieLength, rating, description, textRating;
    private Button showMoreButton;
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
        textRating = findViewById(R.id.detail_rating_text);
        description = findViewById(R.id.detail_description);
        showMoreButton = findViewById(R.id.detail_read_more);
        starOne = findViewById(R.id.detail_star_one);
        starTwo = findViewById(R.id.detail_star_two);
        starThree = findViewById(R.id.detail_star_three);
        starFour = findViewById(R.id.detail_star_four);
        starFive = findViewById(R.id.detail_star_five);

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

            // Set Movie Length
            int[] hoursminutes = minutesToHoursAndMinutes(movie.getMovieLength());
            movieLength.setText(String.format("%dh %dm", hoursminutes[0], hoursminutes[1]));

            // Set Genres
            for(String genre : movie.getGenreIds()) {
                genres.append(genre + "/");
            }

            // Set rating
            setMovieRating(movie.getRatingAverage());
            textRating.setText(String.valueOf(movie.getRatingAverage()));

            // Set description
            description.setText(movie.getDescription());
            showMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("clicked");
                    if(showMoreButton.getText().toString().equalsIgnoreCase("Show more")) {
                        description.setMaxLines(Integer.MAX_VALUE);
                        showMoreButton.setText("Show less");
                    } else {
                        description.setMaxLines(2);
                        showMoreButton.setText("Show more");
                    }
                }
            });
        } else {
            // Something went wrong, show error.
            // TODO: Handle error
            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_LONG).show();
        }
    }

    public void setMovieRating(Double rating) {
        System.out.println(rating);
        if(rating <= 0) {
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(rating > 0 && rating <= 1) {
            Glide.with(this).load(R.drawable.ic_baseline_star_half_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(rating > 1 && rating <= 2) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(rating > 2 && rating <= 3) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_half_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(rating > 3 && rating <= 4) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(rating > 4 && rating <= 5) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_half_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(rating > 5 && rating <= 6) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(rating > 6 && rating <= 7) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_half_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(rating > 7 && rating <= 8) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(rating > 8 && rating <= 9) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_half_24).into(starFive);
        } else {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starFive);
        }
    }

    public int[] minutesToHoursAndMinutes(int minutes) {
        int[] hoursminutes = new int[2];

        hoursminutes[0] = minutes / 60;
        hoursminutes[1] = minutes % 60;

        return hoursminutes;
    }
}