package com.example.movieappschool.ui.detail;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.ui.LoginActivity;
import com.example.movieappschool.ui.home.MovieAdapter;
import com.example.movieappschool.ui.order.OrderActivity;
import com.example.movieappschool.ui.success.OrderSuccessActivity;
import com.example.movieappschool.ui.LoadActivity;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private LocalAppStorage localAppStorage;
    private CinemaDatabaseService cinemaDatabaseService;
    private List<Movie> movies;
    private Movie movie;
    private View toolbar;
    private ImageView backgroundImage, poster, starOne, starTwo, starThree, starFour, starFive, backButton;
    private TextView title, releaseyear, genres, movieLength, rating, description, textRating, loggedIn;
    private Button showMoreButton, orderButton;
    private int movieId;
    private String previousActivity;

    public DetailActivity() {
        localAppStorage = (LocalAppStorage) this.getApplication();
        cinemaDatabaseService = new CinemaDatabaseService();
        movies = localAppStorage.getMovies();
    }

    public String getYearFromDate(String date) {
        return date.split("-")[0];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId", -1);
        previousActivity = intent.getStringExtra("prevActivity");

        toolbar = findViewById(R.id.movie_detail_toolbar);
        toolbar.findViewById(R.id.hamburger_icon).setVisibility(View.INVISIBLE);

        backButton = toolbar.findViewById(R.id.back_icon);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(v -> {
            try {
                Intent backIntent = new Intent(getApplicationContext(), Class.forName(previousActivity));
                backIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getApplicationContext().startActivity(backIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

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

            for (int i = 0; i < movie.getGenreIds().size(); i++) {
                if(i == movie.getGenreIds().size() - 1) {
                    genres.append(movie.getGenreIds().get(i));
                } else {
                    genres.append(movie.getGenreIds().get(i) + "/");
                }
            }

            // Set rating
            setMovieRating(movie.getRatingAverage());
            textRating.setText(String.valueOf(movie.getRatingAverage()));

            // Set description
            description.setText(movie.getDescription());
            showMoreButton.setText(R.string.movie_details_show_more);
            showMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(showMoreButton.getText().toString().equals(getString(R.string.movie_details_show_more))) {
                        description.setMaxLines(Integer.MAX_VALUE);
                        showMoreButton.setText(R.string.movie_details_show_less);
                    } else {
                        description.setMaxLines(2);
                        showMoreButton.setText(R.string.movie_details_show_more);
                    }
                }
            });

            orderButton = findViewById(R.id.detail_order_tickets);
            loggedIn = findViewById(R.id.not_logged_in);
            loggedIn.setVisibility(View.INVISIBLE);

            if (!localAppStorage.getLoggedIn()) {
                orderButton.setVisibility(View.GONE);
                loggedIn.setVisibility(View.VISIBLE);
            }

            loggedIn.setOnClickListener(v -> {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(loginIntent);
            });

            orderButton.setOnClickListener(v -> new Thread(() -> {
                Intent iLoad = new Intent(DetailActivity.this, LoadActivity.class);
                startActivity(iLoad);
                try {
                    cinemaDatabaseService.deleteExpiredShows();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (cinemaDatabaseService.doShowsExist(movieId)) {
                    Intent orderIntent = new Intent(getApplicationContext(), OrderActivity.class);
                    orderIntent.putExtra("prevActivity", getClass().getName());
                    orderIntent.putExtra("movieId", movieId);
                    orderIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(orderIntent);
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, getString(R.string.detail_no_shows_found), Toast.LENGTH_LONG).show();
                    });
                }
            }).start());
        } else {
            // Something went wrong, show error.
            // TODO: Handle error
            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_LONG).show();
        }
    }

    public void setMovieRating(Double rating) {
        long ratingRounded = Math.round(rating);
        System.out.println(ratingRounded);

        if(ratingRounded <= 0.0) {
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(ratingRounded > 0.0 && ratingRounded <= 1.0) {
            Glide.with(this).load(R.drawable.ic_baseline_star_half_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(ratingRounded > 1.0 && ratingRounded <= 2.0) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(ratingRounded > 2.0 && ratingRounded <= 3.0) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_half_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(ratingRounded > 3.0 && ratingRounded <= 4.0) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(ratingRounded > 4.0 && ratingRounded <= 5.0) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_half_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(ratingRounded > 5.0 && ratingRounded <= 6.0) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(ratingRounded > 6.0 && ratingRounded <= 7.0) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_half_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(ratingRounded > 7.0 && ratingRounded <= 8.0) {
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starOne);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starTwo);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starThree);
            Glide.with(this).load(R.drawable.ic_baseline_star_24).into(starFour);
            Glide.with(this).load(R.drawable.ic_baseline_star_border_24).into(starFive);
        } else if(ratingRounded > 8.0 && ratingRounded <= 9.0) {
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