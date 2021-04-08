package com.example.movieappschool.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.Seat;
import com.example.movieappschool.domain.Show;
import com.example.movieappschool.domain.Ticket;
import com.example.movieappschool.logic.CustomPicker;
import com.example.movieappschool.logic.SeatConfigurator;
import com.example.movieappschool.logic.ShowConfigurator;
import com.example.movieappschool.ui.OrderSuccessActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderActivity extends AppCompatActivity {
    private View toolbar;
    private ImageView backButtton;
    private LocalAppStorage localAppStorage;
    CinemaDatabaseService cinemaDatabaseService;
    private SeatConfigurator seatConfigurator;
    private ShowConfigurator showConfigurator;
    private int amountOfChildTickets;
    private int amountOfAdultTickets;
    private int amountOfSeniorTickets;
    private int totalAmountOfTickets;
    private TextView totalOrderSeatsTextView;
    private List<Show> shows;
    private Show selectedShow;
    private List<Integer> occupiedSeats;
    private List<Ticket> tickets;
    private List<Seat> selectedSeats = new ArrayList<>();
    private int movieId;

    public OrderActivity() {
        cinemaDatabaseService = new CinemaDatabaseService();
        localAppStorage = (LocalAppStorage) this.getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId", -1);

        toolbar = findViewById(R.id.order_toolbar);
        toolbar.findViewById(R.id.hamburger_icon).setVisibility(View.INVISIBLE);

        backButtton = toolbar.findViewById(R.id.back_icon);
        backButtton.setVisibility(View.VISIBLE);

        // Threads.
        Thread loadShowsThread = new Thread(() -> {
            shows = cinemaDatabaseService.getAllShowsOfMovie(movieId);

            if (!shows.isEmpty()) {
                selectedShow = shows.get(0);
                Log.d("SELECTED SHOW", selectedShow.toString());
            }
        });

        Thread loadOccupiedSeatsThread = new Thread(() -> {
            occupiedSeats = cinemaDatabaseService.getOccupiedSeats(selectedShow.getShowId());
        });

        Thread seatConfiguratorThread = new Thread(() -> runOnUiThread(() -> {
            seatConfigurator = new SeatConfigurator(totalAmountOfTickets, occupiedSeats, OrderActivity.this, seats -> {
                selectedSeats = seats;
                updateSeatTextView();
                updateTotalPrice();
            });

            // Disable the seats that are occupied.
            seatConfigurator.setOccupiedSeats();

            // Handle the seats that are available.
            seatConfigurator.setAvailableSeats();
        }));

        Thread showConfiguratorThread = new Thread(() -> {
            showConfigurator = new ShowConfigurator(OrderActivity.this, shows, show -> {
                selectedShow = show;
                updateShow();
                Log.d("SELECTED SHOW", show.toString());
            });

            showConfigurator.setUpConfigurator();
        });

        try {
            loadShowsThread.start();
            loadShowsThread.join();

            loadOccupiedSeatsThread.start();
            loadOccupiedSeatsThread.join();

            seatConfiguratorThread.start();
            seatConfiguratorThread.join();

            showConfiguratorThread.start();
            showConfiguratorThread.join();
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
                localAppStorage.setTickets(createTickets());

                Intent orderSuccessIntent = new Intent(getApplicationContext(), OrderSuccessActivity.class);
                orderSuccessIntent.putExtra("movieId", movieId);
                orderSuccessIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                getApplicationContext().startActivity(orderSuccessIntent);
            }
        });
    }

    private void updateShow() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                occupiedSeats = cinemaDatabaseService.getOccupiedSeats(selectedShow.getShowId());
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                seatConfigurator.updateOccupiedSeats(occupiedSeats);
            }
        });

        try {
            t1.start();
            t1.join();

            t2.start();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateTotalTicketAmount(int difference) {
        totalAmountOfTickets += difference;
        seatConfigurator.updateNumberOfTickets(totalAmountOfTickets);
        updateSeatTextView();
    }

    private void updateSeatTextView() {
        totalOrderSeatsTextView = findViewById(R.id.order_seats_total);
        totalOrderSeatsTextView.setText(selectedSeats.size() + " / " + totalAmountOfTickets + " stoelen");
    }

    private void updateTotalPrice() {
        int price = amountOfChildTickets * 7 + amountOfAdultTickets * 11 + amountOfSeniorTickets * 9;

        TextView priceTextView = findViewById(R.id.order_price);
        priceTextView.setText("€" + price);
    }

    private List<Ticket> createTickets() {
        List<Ticket> result = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < amountOfChildTickets; i++) {
            Seat seat = selectedSeats.get(i);

            result.add(new Ticket(random.nextInt(1000 - 0 + 1) + 1000, localAppStorage.getUser().getUserId(), seat.getSeatNumber(), selectedShow.getShowId(), amountOfChildTickets * 7));
        }

        for (int i = 0; i < amountOfAdultTickets; i++) {
            Seat seat = selectedSeats.get(i + amountOfChildTickets);

            result.add(new Ticket(random.nextInt(1000 - 0 + 1) + 1000, localAppStorage.getUser().getUserId(), seat.getSeatNumber(), selectedShow.getShowId(), amountOfAdultTickets * 11));
        }

        for (int i = 0; i < amountOfSeniorTickets; i++) {
            Seat seat = selectedSeats.get(i + amountOfChildTickets + amountOfSeniorTickets);

            result.add(new Ticket(random.nextInt(1000 - 0 + 1) + 1000, localAppStorage.getUser().getUserId(), seat.getSeatNumber(), selectedShow.getShowId(), amountOfAdultTickets * 11));
        }

        Log.d("tickets", result.toString());
        return result;
    }
}