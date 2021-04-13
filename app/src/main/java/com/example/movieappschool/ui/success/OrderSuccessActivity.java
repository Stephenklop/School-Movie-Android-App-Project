package com.example.movieappschool.ui.success;

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
import com.example.movieappschool.ui.ticket.TicketListActivity;

public class OrderSuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_success);

        // Intent
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movieId", -1);
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        int totalTickets = intent.getIntExtra("totalTickets", -1);
        int totalPrice = intent.getIntExtra("totalPrice", -1);

        Movie movie = LocalAppStorage.getMovie(movieId);

        // UI
        ImageView moviePoster = findViewById(R.id.order_success_image);
        if (movie != null) {
            Glide.with(getApplicationContext()).load(movie.getPosterURL()).into(moviePoster);
        }

        TextView movieTitle = findViewById(R.id.order_success_movie_title);
        if (movie != null) {
            movieTitle.setText(movie.getTitle());
        }

        TextView dateText = findViewById(R.id.order_success_date);
        dateText.setText(getResources().getString(R.string.order_success_date) + " " + date);

        TextView timeText = findViewById(R.id.order_success_time);
        timeText.setText(getResources().getString(R.string.order_success_time) + " " + time);

        TextView totalTicketsText = findViewById(R.id.order_success_total_ticket_amount);
        totalTicketsText.setText(getResources().getString(R.string.order_success_tickets) + " " + totalTickets);

        TextView totalPriceText = findViewById(R.id.order_success_total_price);
        totalPriceText.setText(getResources().getString(R.string.order_success_price) + " €" + totalPrice + ",-");

        Button homeButton = findViewById(R.id.order_success_back_home_button);
        homeButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(homeIntent);
        });

        Button ticketsButton = findViewById(R.id.order_success_view_tickets_button);
        ticketsButton.setOnClickListener(v -> {
            Intent ticketsIntent = new Intent(getApplicationContext(), TicketListActivity.class);
            ticketsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(ticketsIntent);
        });
    }
}
