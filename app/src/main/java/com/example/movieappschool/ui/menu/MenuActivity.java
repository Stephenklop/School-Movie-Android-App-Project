package com.example.movieappschool.ui.menu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.MainActivity;
import com.example.movieappschool.R;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.ui.AccountActivity;
import com.example.movieappschool.ui.LoginActivity;
import com.example.movieappschool.ui.RegisterActivity;
import com.example.movieappschool.ui.tickets.TicketsActivity;

public class MenuActivity extends AppCompatActivity {
    private View toolbar;
    private ImageView closeButton;
    private Button logout;
    private LocalAppStorage localAppStorage;
    private TextView home;
    private TextView myTickets;
    private TextView login;
    private TextView myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        localAppStorage = (LocalAppStorage) this.getApplication();

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

        logout = findViewById(R.id.menu_logout);
        logout.setOnClickListener(v -> {
            localAppStorage.setLoggedOut();
            localAppStorage.deleteUser();
            myAccount = findViewById(R.id.menu_account);
            myAccount.setText("Login");
            System.out.println(localAppStorage.getLoggedIn());
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

        myAccount = findViewById(R.id.);

        // Check if user is logged in
        if(!localAppStorage.getLoggedIn()) {
            login = findViewById(R.id.menu_account);
            login.setText("Login");
            login.setOnClickListener(v -> {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

                getApplicationContext().startActivity(loginIntent, options.toBundle());
            });
        } else {
            myAccount = findViewById(R.id.menu_account);
            myAccount.setText("My Account");
            myAccount.setOnClickListener(v -> {
                Intent myAccountIntent = new Intent(getApplicationContext(), AccountActivity.class);
                myAccountIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

                getApplicationContext().startActivity(myAccountIntent, options.toBundle());
            });
        }
    }
}
