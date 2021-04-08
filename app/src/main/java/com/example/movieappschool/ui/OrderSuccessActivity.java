package com.example.movieappschool.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieappschool.MainActivity;
import com.example.movieappschool.R;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.ui.tickets.TicketsActivity;

public class OrderSuccessActivity extends AppCompatActivity {
    private LocalAppStorage localAppStorage;
    private int movieId;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_success);
        localAppStorage = (LocalAppStorage) this.getApplication();
        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId", -1);
        movie = localAppStorage.findMovie(movieId);

        ImageView movieImage = findViewById(R.id.order_success_image);
        Glide.with(getApplicationContext()).load(movie.getPosterURL()).into(movieImage);

        TextView movieTitle = findViewById(R.id.order_success_movie_title);
        movieTitle.setText(movie.getTitle());

        Button homeButton = findViewById(R.id.order_success_back_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getApplicationContext().startActivity(homeIntent);
            }
        });

        Button ticketsButton = findViewById(R.id.order_success_view_tickets_button);
        ticketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ticketIntent = new Intent(getApplicationContext(), TicketsActivity.class);
                ticketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getApplicationContext().startActivity(ticketIntent);
            }
        });
    }
}
