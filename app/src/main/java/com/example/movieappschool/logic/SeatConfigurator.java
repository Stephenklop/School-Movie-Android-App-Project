package com.example.movieappschool.logic;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.domain.Seat;

import java.util.ArrayList;
import java.util.List;

import static com.example.movieappschool.logic.ResourceHelper.getResId;

public class SeatConfigurator {
    private final AppCompatActivity activity;
    private final ConfiguratorListener listener;
    private int numberOfTickets;
    private List<Seat> occupiedSeats;
    private final List<Seat> selectedSeats;


    public SeatConfigurator(int numberOfTickets, List<Seat> occupiedSeats, AppCompatActivity activity, ConfiguratorListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.numberOfTickets = numberOfTickets;
        this.occupiedSeats = occupiedSeats;
        selectedSeats = new ArrayList<>();
        setup();
    }

    public void setup() {
        // Disable the seats that are occupied.
        disableOccupiedSeats();

        // Handle the seats that are available.
        enableAvailableSeats();

        // Check the ticket amount.
        checkTicketLimit();

        // Update the seats text
        updateSeatAmountText();
    }

    // Disable the preoccupied seats.
    public void disableOccupiedSeats() {
        for (Seat seat : occupiedSeats) {
            String seatId = "seat_" + seat.getSeatNumber();

            Button seatButton = activity.findViewById(getResId(seatId, R.id.class));
            seatButton.setEnabled(false);
            seatButton.setBackgroundColor(seatButton.getContext().getResources().getColor(R.color.white, activity.getApplicationContext().getTheme()));
        }
    }

    // Enable the available seats.
    public void enableAvailableSeats() {
        for (int i = 1; i < 51; i++) {
            int index = i;

            if (occupiedSeats.stream().noneMatch(o -> o.getSeatNumber() == index)) {
                String seatIdName = "seat_" + index;
                final boolean[] isToggleOn = {false};

                Button seatButton = activity.findViewById(getResId(seatIdName, R.id.class));
                seatButton.setOnClickListener(v -> {
                    // Flip the toggle.
                    isToggleOn[0] = !isToggleOn[0];

                    // Check if the seat (button) is toggled.
                    if (isToggleOn[0]) {
                        // Seat is toggled.
                        seatButton.setBackgroundColor(activity.getResources().getColor(R.color.design_default_color_primary, activity.getTheme()));
                        addSeat(index);
                    } else {
                        // Seat is not toggled.
                        seatButton.setBackgroundColor(activity.getResources().getColor(R.color.tint5, activity.getTheme()));
                        removeSeat(index);
                    }

                    checkTicketLimit();
                });
            }
        }
    }

    public void setNumberOfTickets(int numberOfTickets) {
        // Check if there are less tickets available.
        if (numberOfTickets < this.numberOfTickets) {
            // Remove n amount of seats to match the new number of tickets (starting at the last selected).
            for (int i = this.selectedSeats.size(); i > numberOfTickets; i--) {
                selectedSeats.remove(i - 1);
            }
        }

        this.numberOfTickets = numberOfTickets;
        checkTicketLimit();
        updateSeatAmountText();
    }

    public void setOccupiedSeats(List<Seat> occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
        this.selectedSeats.clear();
        disableOccupiedSeats();
        enableAvailableSeats();
        checkTicketLimit();
        updateSeatAmountText();
    }

    public boolean allSeatsSelected() {
        return (numberOfTickets == selectedSeats.size());
    }

    // Add a seat seat to the selected seats array.
    private void addSeat(int seatNumber) {
        if (selectedSeats.size() < numberOfTickets) {
            selectedSeats.add(new Seat(seatNumber, getRow(seatNumber)));
            listener.onChange(selectedSeats);
        }

        updateSeatAmountText();
    }

    // Remove a seat from the selected seats array.
    private void removeSeat(int seatNumber) {
        for (int i = 0; i < selectedSeats.size(); i++) {
            Seat seat = selectedSeats.get(i);

            if (seat.getSeatNumber() == seatNumber) {
                selectedSeats.remove(i);
                listener.onChange(selectedSeats);
            }
        }

        updateSeatAmountText();
    }

    // Disable (lock) the remaining seats (meaning except the selected and occupied seats).
    private void disableAllRemainingSeats() {
        for (int i = 1; i < 51; i++) {
            int seatNumber = i;

            if (selectedSeats.stream().noneMatch(o -> o.getSeatNumber() == seatNumber) && occupiedSeats.stream().noneMatch(o -> o.getSeatNumber() == seatNumber)) {
                String seatId = "seat_" + seatNumber;

                Button seatButton = activity.findViewById(getResId(seatId, R.id.class));
                seatButton.setEnabled(false);
                seatButton.setBackgroundColor(seatButton.getContext().getResources().getColor(R.color.orangeEndcolor, activity.getTheme()));
            }
        }
    }

    // Enable (unlock) the remaining seats (meaning except the selected and occupied seats).
    private void enableAllRemainingSeats() {
        for (int i = 1; i < 51; i++) {
            int seatNumber = i;

            if (selectedSeats.stream().noneMatch(o -> o.getSeatNumber() == seatNumber) && occupiedSeats.stream().noneMatch(o -> o.getSeatNumber() == seatNumber)) {
                String seatId = "seat_" + seatNumber;

                Button seatButton = activity.findViewById(getResId(seatId, R.id.class));
                seatButton.setEnabled(true);
                seatButton.setBackgroundColor(activity.getResources().getColor(R.color.tint5, activity.getTheme()));
            }
        }
    }

    // Check the ticket limit to ensure that the ticket amount does not exceed or fall behind the amount of selected seats.
    private void checkTicketLimit() {
        if (selectedSeats.size() >= numberOfTickets) {
            disableAllRemainingSeats();
        } else {
            enableAllRemainingSeats();
        }
    }

    // Update the text field that displays the selected and total ticket amount.
    private void updateSeatAmountText() {
        TextView seatAmountTextView = activity.findViewById(R.id.order_seats_total);
        seatAmountTextView.setText(selectedSeats.size() + " / " + numberOfTickets + " " + activity.getResources().getString(R.string.order_selected_seats));
    }

    // Calculate the correct row based on the seat number.
    private int getRow(int seatNumber) {
        if (seatNumber > 0 && seatNumber < 6) {
            return 1;
        }

        return (int) Math.ceil((seatNumber - 5.0) * 0.111 + 1);
    }
}