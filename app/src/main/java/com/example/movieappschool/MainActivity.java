package com.example.movieappschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.data.MovieAPIService;
import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.ui.home.GridSpacingItemDecoration;
import com.example.movieappschool.ui.home.MovieAdapter;
import com.example.movieappschool.ui.menu.MenuActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final CinemaDatabaseService cinemaDatabaseService;
    private final MovieAPIService movieAPIService;
    private final String API_KEY = "b3dc30d1b882188c9c0161b97d66f032";
    private final LocalAppStorage localAppStorage;
    private List<Movie> mMovies;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Integer> databaseIdsResult;
    private List<Movie> movieList = new ArrayList<>();

    public MainActivity() {
        cinemaDatabaseService = new CinemaDatabaseService();
        movieAPIService = new MovieAPIService(API_KEY, "en-US");
        localAppStorage = (LocalAppStorage) this.getApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSearchBar();

        // Menu
        View toolBar = findViewById(R.id.homepage_toolbar);
        ImageView hamburgerIcon = toolBar.findViewById(R.id.hamburger_icon);

        hamburgerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("prevActivity", getClass().getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

            getApplicationContext().startActivity(intent, options.toBundle());
        });

        // RecyclerView
        recyclerView = findViewById(R.id.homepage_movies);

        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 60, false, 0));

        // Create three new threads.
        Thread cinemaDatabaseThread = new Thread(() -> databaseIdsResult = cinemaDatabaseService.getAllMovieIds());

        Thread movieAPIThread = new Thread(() -> {
            mMovies = movieAPIService.getMoviesByIds(databaseIdsResult);
            localAppStorage.setMovies(mMovies);
        });

        Thread adapterThread = new Thread(() -> {
            Looper.prepare();
            movieList.addAll(mMovies);
            mAdapter = new MovieAdapter(movieList, MainActivity.this);

            SearchView searchBar = findViewById(R.id.homepage_search);

            searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String search) {
                    movieList.clear();
                    movieList.addAll(mMovies);
                    new MovieAdapter(movieList, MainActivity.this).getFilter().filter(search);
                    mAdapter.notifyDataSetChanged();

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String search) {
                    movieList.clear();
                    movieList.addAll(mMovies);
                    new MovieAdapter(movieList, MainActivity.this).getFilter().filter(search);
                    mAdapter.notifyDataSetChanged();

                    return true;
                }
            });

            recyclerView.setAdapter(mAdapter);
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
}