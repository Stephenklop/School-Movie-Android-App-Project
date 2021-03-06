package com.example.movieappschool.ui.menu;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.MainActivity;
import com.example.movieappschool.R;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.logic.Language;
import com.example.movieappschool.ui.AccountActivity;
import com.example.movieappschool.ui.LoadActivity;
import com.example.movieappschool.ui.LoginActivity;
import com.example.movieappschool.ui.detail.DetailActivity;
import com.example.movieappschool.ui.ticket.TicketListActivity;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {
    private View toolbar;
    private ImageView closeButton, flagIcon, flagIconEnglish;
    private TextView logout;
    private LocalAppStorage localAppStorage;
    private TextView home;
    private TextView myTickets;
    private TextView login;
    private TextView myAccount;
    private Locale locale;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Get views
        login = findViewById(R.id.menu_account_login);
        logout = findViewById(R.id.menu_account_logout);
        myTickets = findViewById(R.id.menu_my_tickets);
        myAccount = findViewById(R.id.menu_my_account);
        home = findViewById(R.id.menu_home);
        toolbar = findViewById(R.id.menu_toolbar);
        closeButton = toolbar.findViewById(R.id.close_icon);

        // Initialize local app storage
        localAppStorage = (LocalAppStorage) this.getApplication();

        // Navigate back to the previous activity when clicking on the hamburger icon.
        Intent intent = getIntent();
        String previousActivity = intent.getStringExtra("prevActivity");

        // Setup the toolbar
        toolbar.findViewById(R.id.hamburger_icon).setVisibility(View.INVISIBLE);
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

        flagIcon = toolbar.findViewById(R.id.flag_icon);
        flagIconEnglish = toolbar.findViewById(R.id.flag_icon_english);

        // Check which flag to show
        if(LocalAppStorage.getLanguage().equals(Language.EN_US)) {
            flagIcon.setVisibility(View.VISIBLE);
            flagIconEnglish.setVisibility(View.GONE);
        } else {
            flagIcon.setVisibility(View.GONE);
            flagIconEnglish.setVisibility(View.VISIBLE);
        }

        flagIcon.setOnClickListener(v -> {
            LocalAppStorage.setLanguage(Language.NL_NL);
            flagIconEnglish.setVisibility(View.VISIBLE);
            flagIcon.setVisibility(View.GONE);

            new Thread(() -> setAppLocale("nl")).start();
            finish();
            startActivity(getIntent());
        });

        flagIconEnglish.setOnClickListener(v -> {
            LocalAppStorage.setLanguage(Language.EN_US);
            flagIconEnglish.setVisibility(View.GONE);
            flagIcon.setVisibility(View.VISIBLE);

            new Thread(() -> setAppLocale("en")).start();
            finish();
            startActivity(getIntent());
        });

        // Make home button clickable
        home.setOnClickListener(v -> {
            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

            getApplicationContext().startActivity(homeIntent, options.toBundle());
        });

        // Check if user is not logged in and if so, show login button
        if(!localAppStorage.getLoggedIn()) {
            login.setOnClickListener(v -> {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

                getApplicationContext().startActivity(loginIntent, options.toBundle());
            });

            // If user is logged in
        } else {
            // Hide login
            login.setVisibility(View.GONE);

            // Show Logout
            logout.setVisibility(View.VISIBLE);
            logout.setOnClickListener(v -> {
                localAppStorage.setLoggedOut();
                localAppStorage.deleteUser();

                // Hide logout
                logout.setVisibility(View.GONE);

                // Hide my Account
                myAccount.setVisibility(View.GONE);

                // Hide my Tickets
                myTickets.setVisibility(View.GONE);

                // Show login
                login.setVisibility(View.VISIBLE);
                login.setOnClickListener(view1 -> {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

                    getApplicationContext().startActivity(loginIntent, options.toBundle());
                });

                // Disable back button to pages where you need login
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                sendBroadcast(broadcastIntent);

                Toast.makeText(getApplicationContext(), getString(R.string.logout_successfull), Toast.LENGTH_LONG).show();

                Intent homePageIntent = new Intent(getApplicationContext(), MainActivity.class);
                homePageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
                getApplicationContext().startActivity(homePageIntent, options.toBundle());
            });

            // Show my account
            myAccount.setVisibility(View.VISIBLE);
            myAccount.setOnClickListener(view2 -> {
                Intent myAccountIntent = new Intent(getApplicationContext(), AccountActivity.class);
                myAccountIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

                getApplicationContext().startActivity(myAccountIntent, options.toBundle());
            });

            // Show my tickets
            myTickets.setVisibility(View.VISIBLE);
            myTickets.setOnClickListener(view3 -> {
                Intent iLoad = new Intent(MenuActivity.this, LoadActivity.class);
                startActivity(iLoad);
                Intent myTicketsIntent = new Intent(getApplicationContext(), TicketListActivity.class);
                myTicketsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

                getApplicationContext().startActivity(myTicketsIntent, options.toBundle());
            });
        }
    }

    private void setAppLocale(String localeCode){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if(localeCode.equals("nl")) {
                conf.setLocale(new Locale(localeCode.toLowerCase(), "NL"));
            } else {
                conf.setLocale(new Locale(localeCode.toLowerCase()));
            }
        } else {
            conf.locale = new Locale(localeCode.toLowerCase());
        }
        res.updateConfiguration(conf, dm);
    }
}
