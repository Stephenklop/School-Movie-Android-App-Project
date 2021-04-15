package com.example.movieappschool.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieappschool.R;
import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.ui.detail.*;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // Creating a variable for array list and context
    private List<Movie> mMovies;
    private Context context;

    // Creating a constructor for our variables
    public MovieAdapter(List<Movie> mMovies, Context context) {
        this.mMovies = mMovies;
        this.context = context;
    }

    // Method for filtering our recyclerview items.
    public void filterList(List<Movie> filterList) {
        // The line below is to add our filtered list in our movie array list
        mMovies = filterList;

        // The line below is to notify our adapter to update the recycler view
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // The line below is to inflate our layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        // Set poster
        Glide.with(context).load(movie.getPosterURL()).into(holder.poster);

        // Set title
        holder.title.setText(movie.getTitle());

        // Set rating
        setMovieRating(movie.getRatingAverage(), holder);

        // Make item clickable
        holder.parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("movieId", movie.getId());
            intent.putExtra("prevActivity", "com.example.movieappschool.MainActivity");

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Returning the size of the array list
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Creating variables for our views
        ImageView poster;
        TextView title;
        LinearLayout parentLayout;
        LinearLayout ratingLayout;
        ImageView starOne, starTwo, starThree, starFour, starFive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize our views with their id's
            poster = itemView.findViewById(R.id.item_poster);
            title = itemView.findViewById(R.id.item_title);
            parentLayout = itemView.findViewById(R.id.movie_list_item);
            ratingLayout = itemView.findViewById(R.id.item_rating);
            starOne = itemView.findViewById(R.id.item_star_one);
            starTwo = itemView.findViewById(R.id.item_star_two);
            starThree = itemView.findViewById(R.id.item_star_three);
            starFour = itemView.findViewById(R.id.item_star_four);
            starFive = itemView.findViewById(R.id.item_star_five);
        }
    }

    public void setMovieRating(Double rating, ViewHolder holder) {
        long ratingRounded = Math.round(rating);
        System.out.println(ratingRounded);

        if(ratingRounded <= 0.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(ratingRounded > 0.0 && ratingRounded <= 1.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_half_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(ratingRounded > 1.0 && ratingRounded <= 2.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(ratingRounded > 2.0 && ratingRounded <= 3.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_half_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(ratingRounded > 3.0 && ratingRounded <= 4.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(ratingRounded > 4.0 && ratingRounded <= 5.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_half_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(ratingRounded > 5.0 && ratingRounded <= 6.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(ratingRounded > 6.0 && ratingRounded <= 7.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_half_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(ratingRounded > 7.0 && ratingRounded <= 8.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(ratingRounded > 8.0 && ratingRounded <= 9.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_half_24).into(holder.starFive);
        } else {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starFive);
        }
    }
}
