package com.example.movieappschool.ui.order;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.domain.Seat;
import com.example.movieappschool.logic.SeatConfigurator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private SeatConfigurator seatConfigurator;

    // Input
    private List<Integer> occupiedSeats = Arrays.asList(1, 17, 39, 50);
    private int amountOfSeats = 2;

    // Output
    private List<Seat> selectedSeats = new ArrayList<>();

    public OrderActivity() {
        seatConfigurator = new SeatConfigurator(amountOfSeats, occupiedSeats, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        // Disable the seats that are occupied.
        seatConfigurator.setOccupiedSeats();

        // Handle the seats that are available.
        seatConfigurator.setAvailableSeats();

        Button orderButton = findViewById(R.id.order_availability_button);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Go to order activity
            }
        });

    }
}