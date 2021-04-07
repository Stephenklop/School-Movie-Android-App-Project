package com.example.movieappschool.ui.menu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.MainActivity;
import com.example.movieappschool.R;
import com.example.movieappschool.logic.ResourceHelper;
import com.example.movieappschool.ui.LoginActivity;
import com.example.movieappschool.ui.tickets.TicketsActivity;

public class MenuActivity extends AppCompatActivity {
    private View toolbar;
    private ImageView closeButton;
    private TextView home;
    private TextView myTickets;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Navigate back to the previous activity when clicking on the hamburger icon.
        Intent intent = getIntent();
        String previousActivity = intent.getStringExtra("prevActivity");

        toolbar = findViewById(R.id.menu_toolbar);
        toolbar.findViewById(R.id.hamburger_icon).setVisibility(View.INVISIBLE);

        closeButton = toolbar.findViewById(R.id.close_icon);
        closeButton.setVisibility(View.VISIBLE);

        closeButton.setOnClickListener(v -> {
            Intent backIntent = null;
            try {
                backIntent = new Intent(getApplicationContext(), Class.forName(previousActivity));
                backIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

                getApplicationContext().startActivity(backIntent, options.toBundle());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        home = findViewById(R.id.menu_home);
        home.setOnClickListener(v -> {
            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

            getApplicationContext().startActivity(homeIntent, options.toBundle());
        });

        myTickets = findViewById(R.id.menu_my_tickets);
        myTickets.setOnClickListener(v -> {
            Intent myTicketsIntent = new Intent(getApplicationContext(), TicketsActivity.class);
            myTicketsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

            getApplicationContext().startActivity(myTicketsIntent, options.toBundle());
        });

        login = findViewById(R.id.menu_login);
        login.setOnClickListener(v -> {
            Intent myTicketsIntent = new Intent(getApplicationContext(), LoginActivity.class);
            myTicketsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

            getApplicationContext().startActivity(myTicketsIntent, options.toBundle());
        });
    }
}
