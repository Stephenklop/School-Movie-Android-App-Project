package com.example.movieappschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.movieappschool.ui.LoadActivity;

import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.data.MovieAPIService;
import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.logic.Language;
import com.example.movieappschool.ui.home.GridSpacingItemDecoration;
import com.example.movieappschool.ui.home.MovieAdapter;
import com.example.movieappschool.ui.menu.MenuActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Create final variables
    private final String API_KEY = "b3dc30d1b882188c9c0161b97d66f032";
    private final CinemaDatabaseService cinemaDatabaseService;
    private final MovieAPIService movieAPIService;
    private final LocalAppStorage localAppStorage;

    // Creating variables for our UI components
    private RecyclerView movieRV;
    private List<Integer> databaseIdsResult;

    // Variable for our adapter class and array list
    private MovieAdapter mAdapter;
    private List<Movie> mMovies;

    public MainActivity() {
        cinemaDatabaseService = new CinemaDatabaseService();
        localAppStorage = (LocalAppStorage) this.getApplication();
        movieAPIService = new MovieAPIService(API_KEY, localAppStorage.getLanguage());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set menubar
        setMenuBar();

        // Set search view
        setSearchBar();

        // Initializing our variables
        movieRV = findViewById(R.id.homepage_movies);

        // Create threads
        Thread cinemaDatabaseThread = new Thread(() -> databaseIdsResult = cinemaDatabaseService.getAllMovieIds());
        Thread movieAPIThread = new Thread(() -> {
            mMovies = movieAPIService.getMoviesByIds(databaseIdsResult);
            localAppStorage.setMovies(mMovies);
        });
        Thread adapterThread = new Thread(() -> {
            // Calling the method to build the recyclerview
            buildRecyclerView();
        });

        // Start and join the threads.
        try {
            cinemaDatabaseThread.start();
            cinemaDatabaseThread.join();

            movieAPIThread.start();
            movieAPIThread.join();

            adapterThread.start();
            adapterThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setSearchBar() {
        SearchView searchView = (SearchView) findViewById(R.id.homepage_search);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Inside this method we are calling the method to filter our recycler view
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        // Creating a new array list to filter our data
        ArrayList<Movie> filteredList = new ArrayList<>();

        // Running a for loop to compare elements
        for(Movie item : mMovies) {

            // Checking if the given string matches with the title of any item in our recycler view
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())) {

                // If the item is matched we are adding it to our filtered list
                filteredList.add(item);
            }
        }

        if(filteredList.isEmpty()) {
            // If no item is added in our filtered list, we are displaying a toast message that no data is found
            movieRV.setVisibility(View.GONE);
            findViewById(R.id.homepage_movies_no_tickets_found).setVisibility(View.VISIBLE);
        } else {
            movieRV.setVisibility(View.VISIBLE);
            findViewById(R.id.homepage_movies_no_tickets_found).setVisibility(View.GONE);
            mAdapter.filterList(filteredList);
        }
    }

    private void buildRecyclerView() {
        // Below, we create a new array list
        mMovies = movieAPIService.getMoviesByIds(databaseIdsResult);

        // Initializing our adapter class
        mAdapter = new MovieAdapter(mMovies, MainActivity.this);

        // Adding layout manager to our recycler view
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        movieRV.addItemDecoration(new GridSpacingItemDecoration(2, 60, false, 0));
        movieRV.setHasFixedSize(true);

        // Setting layout manager to our recycler view
        movieRV.setLayoutManager(layoutManager);

        // Connecting adapter to our recycler view
        movieRV.setAdapter(mAdapter);
    }

    private void setMenuBar() {
        // Get views
        View toolBar = findViewById(R.id.homepage_toolbar);
        ImageView hamburgerIcon = toolBar.findViewById(R.id.hamburger_icon);

        // Set onclick listeners
        hamburgerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("prevActivity", getClass().getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

            getApplicationContext().startActivity(intent, options.toBundle());
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        System.out.println("Back button is disabled");
    }
}