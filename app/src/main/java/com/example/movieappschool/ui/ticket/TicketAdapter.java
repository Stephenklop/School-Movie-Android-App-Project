package com.example.movieappschool.ui.ticket;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieappschool.R;
import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.domain.Ticket;
import com.example.movieappschool.logic.Converter;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    // Creating a variable for array list and context
    private List<Ticket> mTickets;
    private Context context;

    // Creating a constructor for our variables
    public TicketAdapter(List<Ticket> mTickets, Context context) {
        this.mTickets = mTickets;
        this.context = context;
    }

    // Method for filtering our recyclerview items.
    public void filterList(List<Ticket> filterList) {

        // The line below is to add our filtered list in our ticket array list
        mTickets = filterList;

        // The line below is to notify our adapter to update the recycler view
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // The line below is to inflate our layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = mTickets.get(position);

        String date = ticket.getShow().getDate();
        int rowNr = ticket.getRowNumber();
        int chairNr = ticket.getSeatNumber();
        String time = ticket.getShow().getTime();
        int hall = ticket.getShow().getHallId();
        String URL = ticket.getShow().getMovie().getPosterURL();

        Glide.with(this.context).load(URL).into(holder.ticket_image);

        holder.ticket_date.setText(context.getResources().getString(R.string.ticket_date) + " " + date);
        holder.ticket_hall.setText(context.getResources().getString(R.string.ticket_hall) + " " + hall);
        holder.ticket_time.setText(context.getResources().getString(R.string.ticket_time) + " " + time);
        holder.ticket_row.setText(context.getResources().getString(R.string.ticket_row) + " " + rowNr);
        holder.ticket_chair.setText(context.getResources().getString(R.string.ticket_seat) + " " + chairNr);

        // qr button
        // inflate the layout of the popup window

        holder.ticket_QRcode.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.ticket_list_qr, null);

            // Create the popup background blur

            // create the popup window
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, focusable);

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        // Returning the size of the array list
        return mTickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ticket_image;
        TextView ticket_date;
        TextView ticket_hall;
        TextView ticket_time;
        TextView ticket_row;
        TextView ticket_chair;
        Button ticket_QRcode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize our views with their id's
            ticket_image = itemView.findViewById(R.id.ticket_image);
            ticket_date = itemView.findViewById(R.id.ticket_date);
            ticket_hall = itemView.findViewById(R.id.ticket_hall);
            ticket_time = itemView.findViewById(R.id.ticket_time);
            ticket_row = itemView.findViewById(R.id.ticket_row);
            ticket_chair = itemView.findViewById(R.id.ticket_chair);
            ticket_QRcode = itemView.findViewById(R.id.ticket_QRcode);
        }
    }
}
