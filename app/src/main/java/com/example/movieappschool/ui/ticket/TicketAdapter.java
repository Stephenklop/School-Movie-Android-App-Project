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

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> implements Filterable {
    private List<Ticket> tickets;
    private Context context;
    private List<Ticket> mTicketsFull;

    public TicketAdapter(List<Ticket> tickets, Context context) {
        this.tickets = tickets;
        mTicketsFull = new ArrayList<>(tickets);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);

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
            float width = Converter.dpToPx(context, 275);
            float height = Converter.dpToPx(context, 324);
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
        return tickets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ticket_image;
        TextView ticket_date;
        TextView ticket_hall;
        TextView ticket_time;
        TextView ticket_row;
        TextView ticket_chair;
        Button ticket_QRcode;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ticket_image = itemView.findViewById(R.id.ticket_image);
            ticket_date = itemView.findViewById(R.id.ticket_date);
            ticket_hall = itemView.findViewById(R.id.ticket_hall);
            ticket_time = itemView.findViewById(R.id.ticket_time);
            ticket_row = itemView.findViewById(R.id.ticket_row);
            ticket_chair = itemView.findViewById(R.id.ticket_chair);
            ticket_QRcode = itemView.findViewById(R.id.ticket_QRcode);
        }
    }

    @Override
    public Filter getFilter() {
        return ticketFilter;
    }

    private Filter ticketFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Ticket> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(mTicketsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Ticket ticket : mTicketsFull) {
                    System.out.println(ticket.getShow().getMovie().getTitle().toLowerCase().contains(filterPattern));
                    if(ticket.getShow().getMovie().getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(ticket);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            tickets.clear();
            tickets.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
