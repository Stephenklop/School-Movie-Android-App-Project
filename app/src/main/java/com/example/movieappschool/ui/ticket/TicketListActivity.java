package com.example.movieappschool.ui.ticket;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.Ticket;
import com.example.movieappschool.ui.menu.MenuActivity;

import java.util.List;

public class TicketListActivity extends AppCompatActivity {
    private final CinemaDatabaseService cinemaDatabaseService;
    private LocalAppStorage localAppStorage;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Ticket> tickets;

    public TicketListActivity() {
        cinemaDatabaseService = new CinemaDatabaseService();
        localAppStorage = (LocalAppStorage) this.getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);

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

        recyclerView = findViewById(R.id.tickets_list_items);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        Thread loadTicketsThread = new Thread(() -> {
            tickets = cinemaDatabaseService.getTicketList(localAppStorage.getUser().getUserId());
        });

        Thread adapterThread = new Thread(() -> {
            // specify an adapter (see also next example)
            mAdapter = new TicketAdapter(tickets, this);
            recyclerView.setAdapter(mAdapter);
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
}
