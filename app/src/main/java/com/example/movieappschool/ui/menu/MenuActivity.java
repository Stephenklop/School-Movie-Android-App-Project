package com.example.movieappschool.ui.menu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.logic.ResourceHelper;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Navigate back to the previous activity when clicking on the hamburger icon.
        Intent intent = getIntent();
        String previousActivity = intent.getStringExtra("prevActivity");

        View view = findViewById(R.id.menu_toolbar);
        ImageView hamburgerIcon = view.findViewById(R.id.hamburger_icon);

        hamburgerIcon.setOnClickListener(v -> {
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
    }
}
