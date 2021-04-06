package com.example.movieappschool.logic;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.domain.Seat;

import java.util.ArrayList;
import java.util.List;

import static com.example.movieappschool.logic.ResourceHelper.getResId;

public class SeatConfigurator {
    private final List<Seat> selectedSeats;
    private final int numberOfTickets;
    private final List<Integer> occupiedSeats;
    private final AppCompatActivity activityView;
    private boolean selectionDisabled;

    public SeatConfigurator(int numberOfTickets, List<Integer> occupiedSeats, AppCompatActivity activityView) {
        selectedSeats = new ArrayList<>();
        this.numberOfTickets = numberOfTickets;
        this.occupiedSeats = occupiedSeats;
        this.activityView = activityView;
        selectionDisabled = false;
    }

    // Disable the preoccupied seats.
    public void setOccupiedSeats() {
        for (int seat : occupiedSeats) {
            String seatId = "seat_" + seat;

            Button seatButton = activityView.findViewById(getResId(seatId, R.id.class));
            seatButton.setEnabled(false);
            seatButton.setBackgroundColor(seatButton.getContext().getResources().getColor(R.color.white, activityView.getApplicationContext().getTheme()));
        }
    }

    // Set a onClickListener on the remaining available seats.
    public void setAvailableSeats() {
        for (int i = 1; i < 51; i++) {
            int index = i;

            if (!occupiedSeats.contains(index)) {
                String seatIdName = "seat_" + index;
                final boolean[] isToggleOn = {false};

                Button seatButton = activityView.findViewById(getResId(seatIdName, R.id.class));
                seatButton.setOnClickListener(v -> {
                    // Flip the toggle.
                    isToggleOn[0] = !isToggleOn[0];

                    // Check if the seat (button) is toggled.
                    if (isToggleOn[0]) {
                        // Seat is toggled.
                        seatButton.setBackgroundColor(activityView.getResources().getColor(R.color.design_default_color_primary, activityView.getTheme()));
                        addSeat(index);
                    } else {
                        // Seat is not toggled.
                        seatButton.setBackgroundColor(activityView.getResources().getColor(R.color.tint5, activityView.getTheme()));
                        removeSeat(index);
                    }

                    checkTicketLimit();
                });
            }
        }
    }

    // Add a seat seat to the selected seats array.
    private void addSeat(int seatNumber) {
        if (selectedSeats.size() < numberOfTickets) {
            selectedSeats.add(new Seat(seatNumber, getRow(seatNumber)));
        }
    }

    // Remove a seat from the selected seats array.
    private void removeSeat(int seatNumber) {
        for (int i = 0; i < selectedSeats.size(); i++) {
            Seat seat = selectedSeats.get(i);

            if (seat.getSeatNumber() == seatNumber) {
                selectedSeats.remove(i);
            }
        }
    }

    // Disable (lock) the remaining seats (meaning except the selected and occupied seats).
    private void disableRemainingSeats() {
        for (int i = 1; i < 51; i++) {
            int seatNumber = i;

            if (selectedSeats.stream().noneMatch(o -> o.getSeatNumber() == seatNumber) && !occupiedSeats.contains(seatNumber)) {
                String seatId = "seat_" + seatNumber;

                Button seatButton = activityView.findViewById(getResId(seatId, R.id.class));
                seatButton.setEnabled(false);
                seatButton.setBackgroundColor(seatButton.getContext().getResources().getColor(R.color.orangeEndcolor, activityView.getTheme()));
            }
        }

        selectionDisabled = true;
    }

    // Enable (unlock) the remaining seats (meaning except the selected and occupied seats).
    private void enableRemainingSeats() {
        for (int i = 1; i < 51; i++) {
            int seatNumber = i;

            if (selectedSeats.stream().noneMatch(o -> o.getSeatNumber() == seatNumber) && !occupiedSeats.contains(seatNumber)) {
                String seatId = "seat_" + seatNumber;

                Button seatButton = activityView.findViewById(getResId(seatId, R.id.class));
                seatButton.setEnabled(true);
                seatButton.setBackgroundColor(activityView.getResources().getColor(R.color.tint5, activityView.getTheme()));
            }
        }

        selectionDisabled = false;
    }

    // Check the ticket limit to ensure that the ticket amount does not exceed or fall behind the amount of selected seats.
    private void checkTicketLimit() {
        if (selectedSeats.size() >= numberOfTickets) {
            disableRemainingSeats();
        } else if (selectionDisabled) {
            enableRemainingSeats();
        }
    }

    // Calculate the correct row based on the seat number.
    private int getRow(int seatNumber) {
        if (seatNumber > 0 && seatNumber < 6) {
            return 1;
        }

        return (int) Math.ceil((seatNumber - 5.0) * 0.111 + 1);
    }
}