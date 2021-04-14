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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> implements Filterable {
    private List<Movie> mMovies;
    private List<Movie> mMoviesFull;
    private Context context;

    public MovieAdapter(List<Movie> mMovies, Context context) {
        this.mMovies = mMovies;
        mMoviesFull = new ArrayList<>(mMovies);
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
        // Set Poster
        Glide.with(context).load(mMovies.get(position).getPosterURL()).into(holder.poster);

        // Set Title
        holder.title.setText(mMovies.get(position).getTitle());

        // Set Rating
        setMovieRating(mMovies.get(position).getRatingAverage(), holder);

        holder.parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", mMovies.get(position).getId());
            intent.putExtra("prevActivity", "com.example.movieappschool.MainActivity");

            context.startActivity(intent);
        });
    }

    public void setMovieRating(Double rating, ViewHolder holder) {
        System.out.println(rating);
        if(rating <= 0.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(rating > 0.0 && rating <= 1.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_half_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(rating > 1.0 && rating <= 2.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(rating > 2.0 && rating <= 3.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_half_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(rating > 3.0 && rating <= 4.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(rating > 4.0 && rating <= 5.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_half_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(rating > 5.0 && rating <= 6.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(rating > 6.0 && rating <= 7.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_half_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(rating > 7.0 && rating <= 8.0) {
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starOne);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starTwo);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starThree);
            Glide.with(context).load(R.drawable.ic_baseline_star_24).into(holder.starFour);
            Glide.with(context).load(R.drawable.ic_baseline_star_border_24).into(holder.starFive);
        } else if(rating > 8.0 && rating <= 9.0) {
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

    @Override
    public int getItemCount() {
        // Return the Movie list size.
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Define the ViewHolder.
        ImageView poster;
        TextView title;
        LinearLayout parentLayout;
        LinearLayout ratingLayout;
        ImageView starOne, starTwo, starThree, starFour, starFive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

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

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(mMoviesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Movie movie : mMoviesFull) {
                    System.out.println(movie.getTitle().toLowerCase().contains(filterPattern));
                    if(movie.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMovies.clear();
            mMovies.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
