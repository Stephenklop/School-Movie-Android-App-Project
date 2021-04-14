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
    private final CinemaDatabaseService cinemaDatabaseService;
    private LocalAppStorage localAppStorage;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Ticket> tickets;
    private List<Ticket> ticketList = new ArrayList<>();

    public TicketListActivity() {
        cinemaDatabaseService = new CinemaDatabaseService();
        localAppStorage = (LocalAppStorage) this.getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);

        setSearchBar();

        // Menu
        View toolBar = findViewById(R.id.tickets_list_toolbar);
        ImageView hamburgerIcon = toolBar.findViewById(R.id.hamburger_icon);

        hamburgerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("prevActivity", getClass().getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

            getApplicationContext().startActivity(intent, options.toBundle());
        });

        recyclerView = findViewById(R.id.ticket_list_items);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        Thread loadTicketsThread = new Thread(() -> {
            cinemaDatabaseService.deleteExpiredTickets();
            tickets = cinemaDatabaseService.getTicketList(localAppStorage.getUser().getUserId());
        });

        Thread adapterThread = new Thread(() -> {
            // specify an adapter (see also next example)
            if (tickets.isEmpty()) {
                System.out.println("No data");
                recyclerView.setVisibility(View.GONE);
                findViewById(R.id.tickets_list_no_tickets_found).setVisibility(View.VISIBLE);
            } else {
                Looper.prepare();
                ticketList.addAll(tickets);
                mAdapter = new TicketAdapter(ticketList, this);

                SearchView searchBar = findViewById(R.id.tickets_list_search);

                searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String search) {
                        mAdapter.notifyDataSetChanged();
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String search) {
                        ticketList.clear();
                        ticketList.addAll(tickets);
                        new TicketAdapter(ticketList, TicketListActivity.this).getFilter().filter(search);
                        mAdapter.notifyDataSetChanged();

                        return true;
                    }
                });

                recyclerView.setAdapter(mAdapter);
            }
        });

        try {
            loadTicketsThread.start();
            loadTicketsThread.join();

            adapterThread.start();
            adapterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setSearchBar() {
        SearchView searchView = (SearchView) findViewById(R.id.tickets_list_search);
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


    @Override
    public void onBackPressed() {
        System.out.println("Back button is disabled");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if(v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if(!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
