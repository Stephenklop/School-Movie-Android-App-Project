package com.example.movieappschool.ui.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.domain.Seat;
import com.example.movieappschool.domain.Show;
import com.example.movieappschool.logic.CustomPicker;
import com.example.movieappschool.logic.SeatConfigurator;
import com.example.movieappschool.logic.ShowConfigurator;
import com.example.movieappschool.ui.success.OrderSuccessActivity;
import com.example.movieappschool.ui.LoginActivity;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private View toolbar;
    private ImageView backButton;
    private CinemaDatabaseService cinemaDatabaseService;
    private int movieId;
    private List<Show> shows;
    private ShowConfigurator showConfigurator;
    private SeatConfigurator seatConfigurator;
    private Show selectedShow;
    private int amountOfChildTickets;
    private int amountOfAdultTickets;
    private int amountOfSeniorTickets;
    private int totalTicketAmount;
    private List<Seat> occupiedSeats;
    private List<Seat> selectedSeats;

    public OrderActivity() {
        cinemaDatabaseService = new CinemaDatabaseService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        Intent intent = getIntent();
        // TODO: Check if movieId == -1 (= error)
        movieId = intent.getIntExtra("movieId", -1);
        //movieId = 791373;

        // Set logout receiver
        setLogoutReceiver();

        toolbar = findViewById(R.id.order_toolbar);
        toolbar.findViewById(R.id.hamburger_icon).setVisibility(View.INVISIBLE);

        backButton = toolbar.findViewById(R.id.back_icon);
        backButton.setVisibility(View.VISIBLE);

        // Threads.
        Thread showConfiguratorThread = new Thread(() -> {
            shows = cinemaDatabaseService.getAllShowsOfMovie(movieId);

            showConfigurator = new ShowConfigurator(shows, OrderActivity.this, object -> {
                selectedShow = (Show) object;
                Log.d("CURRENT SHOW", object.toString());
                // Update seat configurator.
                updateSeats(selectedShow);
            });

            // Set the selected show to a default.
            selectedShow = showConfigurator.getSelectedShow();
        });

        Thread ticketsSelectorThread = new Thread(() -> {
            new CustomPicker(findViewById(R.id.child_ticket_picker), 0, 10, value -> {
                int difference = value - amountOfChildTickets;
                amountOfChildTickets = value;

                updateTotalTicketAmount(difference);
            });

            new CustomPicker(findViewById(R.id.adult_ticket_picker), 0, 10, value -> {
                int difference = value - amountOfAdultTickets;
                amountOfAdultTickets = value;

                updateTotalTicketAmount(difference);
            });

            new CustomPicker(findViewById(R.id.senior_ticket_picker), 0, 10, value -> {
                int difference = value - amountOfSeniorTickets;
                amountOfSeniorTickets = value;

                updateTotalTicketAmount(difference);
            });
        });

        Thread seatConfiguratorThread = new Thread(() -> {
            occupiedSeats = cinemaDatabaseService.getOccupiedSeats(selectedShow.getShowId());

            runOnUiThread(() -> seatConfigurator = new SeatConfigurator(totalTicketAmount, occupiedSeats, OrderActivity.this, object -> selectedSeats = (List<Seat>) object));
        });

        try {
            showConfiguratorThread.start();
            showConfiguratorThread.join();

            ticketsSelectorThread.start();
            ticketsSelectorThread.join();

            seatConfiguratorThread.start();
            seatConfiguratorThread.join();
        } catch (InterruptedException e) {
            // TODO: Handle error
            e.printStackTrace();
        }

        Button orderButton = findViewById(R.id.order_availability_button);
        orderButton.setOnClickListener(v -> {
            if (selectedSeats != null && selectedSeats.size() > 0 && seatConfigurator.allSeatsSelected()) {
                Intent orderSuccessIntent = new Intent(getApplicationContext(), OrderSuccessActivity.class);
                //orderSuccessIntent.putExtra("movieId", movieId);
                orderSuccessIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("ORDER BUTTON", "READY TO ORDER!");
                Log.d("ALL SEATS SELECTED?", String.valueOf(seatConfigurator.allSeatsSelected()));
                getApplicationContext().startActivity(orderSuccessIntent);
            } else {
                Toast.makeText(this, "Controleer uw stoelselectie.", Toast.LENGTH_LONG).show();
            }
            //Log.d("ORDER BUTTON", selectedSeats.toString());
        });
    }

    private void updateSeats(Show show) {
        new Thread(() -> {
            occupiedSeats = cinemaDatabaseService.getOccupiedSeats(show.getShowId());

            runOnUiThread(() -> {
                seatConfigurator.setOccupiedSeats(occupiedSeats);
            });
        }).start();
    }

    private void updateTotalTicketAmount(int difference) {
        totalTicketAmount += difference;
        seatConfigurator.setNumberOfTickets(totalTicketAmount);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        int totalPrice = amountOfChildTickets * 7 + amountOfAdultTickets * 11 + amountOfSeniorTickets * 9;
        TextView priceTextView = findViewById(R.id.order_total_price);
        priceTextView.setText("€" + totalPrice + ",-");
    }

    private void setLogoutReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                finish();
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        }, intentFilter);
    }
}