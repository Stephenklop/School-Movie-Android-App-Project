package com.example.movieappschool.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieappschool.R;
import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.ui.detail.*;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> mMovies;
    private Context context;

    public MovieAdapter(List<Movie> mMovies, Context context) {
        this.mMovies = mMovies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the list item layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the data and onClickListener() on the list item.
        Glide.with(context).load(mMovies.get(position).getPosterURL()).into(holder.poster);
        holder.title.setText(mMovies.get(position).getTitle());
        holder.rating.setText(String.valueOf(mMovies.get(position).getRatingAverage()));

        holder.parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", mMovies.get(position).getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Return the Movie list size.
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Define the ViewHolder.
        ImageView poster;
        TextView title;
        TextView rating;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.item_poster);
            title = itemView.findViewById(R.id.item_title);
            rating = itemView.findViewById(R.id.item_rating);
            parentLayout = itemView.findViewById(R.id.movie_list_item);
        }
    }
}
