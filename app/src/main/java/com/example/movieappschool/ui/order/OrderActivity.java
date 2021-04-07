package com.example.movieappschool.ui.order;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.domain.Seat;
import com.example.movieappschool.domain.Ticket;
import com.example.movieappschool.logic.CustomPicker;
import com.example.movieappschool.logic.SeatConfigurator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    CinemaDatabaseService cinemaDatabaseService;
    private SeatConfigurator seatConfigurator;
    private int amountOfChildTickets;
    private int amountOfAdultTickets;
    private int amountOfSeniorTickets;
    private int totalAmountOfTickets;
    private TextView totalOrderSeatsTextView;
    private List<Integer> occupiedSeats;

    // Output
    private List<Seat> selectedSeats = new ArrayList<>();

    public OrderActivity() {
        cinemaDatabaseService = new CinemaDatabaseService();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        // Threads.
        Thread cinemaDatabaseThread = new Thread(() -> occupiedSeats = cinemaDatabaseService.getOccupiedSeats(1));

        Thread seatConfiguratorThread = new Thread(() -> runOnUiThread(() -> {
            seatConfigurator = new SeatConfigurator(totalAmountOfTickets, occupiedSeats, OrderActivity.this, seats -> {
                selectedSeats = seats;
                updateSeatTextView();
            });

            // Disable the seats that are occupied.
            seatConfigurator.setOccupiedSeats();

            // Handle the seats that are available.
            seatConfigurator.setAvailableSeats();
        }));

        try {
            cinemaDatabaseThread.start();
            cinemaDatabaseThread.join();

            seatConfiguratorThread.start();
            seatConfiguratorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Child tickets picker.
        new CustomPicker(findViewById(R.id.child_ticket_picker), 0, 10, value -> {
            int difference = value - amountOfChildTickets;
            amountOfChildTickets = value;

            updateTotalTicketAmount(difference);
        });

        // Adult tickets picker.
        new CustomPicker(findViewById(R.id.adult_ticket_picker), 0, 10, value -> {
            int difference = value - amountOfAdultTickets;
            amountOfAdultTickets = value;

            updateTotalTicketAmount(difference);
        });

        // Senior tickets picker.
        new CustomPicker(findViewById(R.id.senior_ticket_picker), 0, 10, value -> {
            int difference = value - amountOfSeniorTickets;
            amountOfSeniorTickets = value;

            updateTotalTicketAmount(difference);
        });

        Button orderButton = findViewById(R.id.order_availability_button);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Go to order activity
            }
        });
    }

    private void updateTotalTicketAmount(int difference) {
        totalAmountOfTickets += difference;
        seatConfigurator.updateNumberOfTickets(totalAmountOfTickets);
        updateSeatTextView();
        Log.d("NEW TICKET AMOUNT", String.valueOf(totalAmountOfTickets));
    }

    private void updateSeatTextView() {
        totalOrderSeatsTextView = findViewById(R.id.order_seats_total);
        totalOrderSeatsTextView.setText(selectedSeats.size() + " / " + totalAmountOfTickets + " stoelen");
    }
}