package com.example.movieappschool.ui.ticket;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieappschool.R;
import com.example.movieappschool.domain.Ticket;
import com.example.movieappschool.logic.Converter;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {
    private List<Ticket> tickets;
    private Context context;

    public TicketAdapter(List<Ticket> tickets, Context context) {
        this.tickets = tickets;
        this.context = context;
        Log.d("RECYCLERVIEWADAPTER", tickets.toString());
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
        //Movie movie = getMovie(show.getMovieId());

        String date = ticket.getShow().getDate();
        int rowNr;
        rowNr = ticket.getRowNumber();
        int chairNr;
        chairNr = ticket.getSeatNumber();
        String time;
        time = ticket.getShow().getTime();
        int hall;
        hall = ticket.getShow().getHallId();
        String URL;
        URL = ticket.getShow().getMovie().getPosterURL();



        /*
        int showId = getShowId(localAppStorage.getTicketList().get(position));
        this.showId = showId;
        int movieId = getMovieId(getShowId(localAppStorage.getTicketList().get(position)));
        this.movieId = movieId;
        String date = getDate(showId);
        int hall = getHall(this.showId);
        String time = getTime(this.showId);
        int chairNr = getChair(localAppStorage.getTicketList().get(position));
        int rowNr = getRowNr(chairNr);
        String URL = getURL(movieId);
        */




        Glide.with(this.context).load(URL).into(holder.ticket_image);

        holder.ticket_date.setText("Datum: " + date);
        holder.ticket_hall.setText("Zaal: " + hall);
        holder.ticket_time.setText("Tijd: " + time);
        holder.ticket_row.setText("Rij: " + rowNr);
        holder.ticket_chair.setText("Stoel: " + chairNr);



        //qr button
        PopupWindow popUp;
        boolean click = true;
        // inflate the layout of the popup window

        holder.ticket_QRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.ticket_list_qr, null);

                // create the popup window
                float width = Converter.dpToPx(context, 275);
                float height = Converter.dpToPx(context, 324);
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, (int) width, (int) height, focusable);

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
            }

        });
    }

    /*
    //returns showid as int
    private int getShowId(Ticket ticket) {
        return ticket.getShow();
    }

    //returns -1 if not available in list
    private int getMovieId(int showId){
        for(int i = 0; i < localAppStorage.getShowList().size(); i++) {
            if(localAppStorage.getShowList().get(i).getShowId() == showId){
                return localAppStorage.getShowList().get(i).getMovieId();
            }
        }
        return -1;
    }

    //returns null if not available in list
    private String getDate(int showId) {
        for(int i = 0; i < localAppStorage.getShowList().size(); i++) {
            if(localAppStorage.getShowList().get(i).getShowId() == showId){
                return localAppStorage.getShowList().get(i).getDate();
            }
        }
        return null;
    }

    //returns -1 if not available in list
    private int getHall(int showId) {
        for(int i = 0; i < localAppStorage.getShowList().size(); i++) {
            if(localAppStorage.getShowList().get(i).getShowId() == showId){
                return localAppStorage.getShowList().get(i).getHallId();
            }
        }
        return -1;
    }

    //returns null if not available in list
    private String getTime(int showId) {
        for(int i = 0; i < localAppStorage.getShowList().size(); i++) {
            if(localAppStorage.getShowList().get(i).getShowId() == showId){
                return localAppStorage.getShowList().get(i).getTime();
            }
        }
        return null;
    }

    //returns chairnr
    private int getChair(Ticket ticket) {
        return ticket.getSeatNumber();
    }

    //returns -1 if not available in list
    private int getRowNr(int chairNr) {
        for(int i = 0; i < localAppStorage.getSeatList().size(); i++) {
            if(localAppStorage.getSeatList().get(i).getSeatNumber() == chairNr){
                return localAppStorage.getSeatList().get(i).getRowNumber();
            }
        }
        return -1;
    }

    //return null if no url is available
    private String getURL(int movieId) {
        for(int i = 0; i < localAppStorage.getMovies().size(); i++) {
            if(localAppStorage.getMovies().get(i).getId() == movieId){
                return localAppStorage.getMovies().get(i).getPosterURL();
            }
        }
        return null;
    }
    */

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
}
