package com.example.movieappschool.ui.ticket;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieappschool.MainActivity;
import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.Ticket;
import com.example.movieappschool.ui.home.GridSpacingItemDecoration;
import com.example.movieappschool.ui.home.MovieAdapter;
import com.example.movieappschool.ui.menu.MenuActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TicketListActivity extends AppCompatActivity {

    // Create final variables
    private final CinemaDatabaseService cinemaDatabaseService;
    private final LocalAppStorage localAppStorage;

    // Creating variables for our UI components
    private RecyclerView ticketRV;

    // Variable for our adapter class and array list
    private TicketAdapter mAdapter;
    private List<Ticket> mTickets;

    public TicketListActivity() {
        cinemaDatabaseService = new CinemaDatabaseService();
        localAppStorage = (LocalAppStorage) this.getApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);

        // Set menubar
        setMenuBar();

        // Set search view
        setSearchBar();

        // Initializing our variables
        ticketRV = findViewById(R.id.ticket_list_items);

        // Create threads
        Thread cinemaDatabaseThread = new Thread(() -> {
            cinemaDatabaseService.deleteExpiredTickets();
            mTickets = cinemaDatabaseService.getTicketList(localAppStorage.getUser().getUserId());
        });
        Thread adapterThread = new Thread(() -> {
            buildRecyclerView();
        });

        // Start and join the threads.
        try {
            cinemaDatabaseThread.start();
            cinemaDatabaseThread.join();

            adapterThread.start();
            adapterThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setMenuBar() {
        // Get views
        View toolBar = findViewById(R.id.tickets_list_toolbar);
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

    private void setSearchBar() {
        SearchView searchView = (SearchView) findViewById(R.id.tickets_list_search);
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
        ArrayList<Ticket> filteredList = new ArrayList<>();

        // Running a for loop to compare elements
        for(Ticket item : mTickets) {

            // Checking if the given string matches with the title of any item in our recycler view
            if(item.getShow().getMovie().getTitle().toLowerCase().contains(text.toLowerCase())) {

                // If the item is matches we are adding it to our filtered list
                filteredList.add(item);
            }
        }

        if(filteredList.isEmpty()) {
            // If no item is added in our filtered list, we are displaying a toast message that no data if found
            Toast.makeText(this, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
            ticketRV.setVisibility(View.GONE);
            findViewById(R.id.tickets_list_no_tickets_found).setVisibility(View.VISIBLE);
        } else {
            mAdapter.filterList(filteredList);
        }
    }

    private void buildRecyclerView() {
        // Initializing our adapter class
        mAdapter = new TicketAdapter(mTickets, this);

        // Adding layout manager to our recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ticketRV.setHasFixedSize(true);

        // Setting layout manager to our recycler view
        ticketRV.setLayoutManager(layoutManager);

        // Connecting adapter to our recycler view
        ticketRV.setAdapter(mAdapter);
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
