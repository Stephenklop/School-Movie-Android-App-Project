package com.example.movieappschool.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordActivity extends AppCompatActivity {
    private CinemaDatabaseService cinemaDatabaseService;
    private LocalAppStorage localAppStorage;
    private Button mChangePasswordButton;
    private View toolbar;
    private ImageView backButton;
    private EditText mOldPasswordEdit, mNewPasswordEdit;
    private User mUser;
    private String mUsername, mOldPassword, previousActivity;

    public PasswordActivity() {
        localAppStorage = (LocalAppStorage) this.getApplication();
        cinemaDatabaseService = new CinemaDatabaseService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        Intent intent = getIntent();
        previousActivity = intent.getStringExtra("prevActivity");

        toolbar = findViewById(R.id.change_password_toolbar);
        toolbar.findViewById(R.id.hamburger_icon).setVisibility(View.INVISIBLE);

        backButton = toolbar.findViewById(R.id.back_icon);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(v -> {
            try {
                Intent backIntent = new Intent(getApplicationContext(), Class.forName(previousActivity));
                backIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getApplicationContext().startActivity(backIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                e.getMessage();
            }
        });

        mChangePasswordButton = findViewById(R.id.change_password_submit);
        mOldPasswordEdit = findViewById(R.id.change_password_old_password);
        mNewPasswordEdit = findViewById(R.id.change_password_new_password);

        mUser = localAppStorage.getUser();

        mUsername = mUser.getUsername();
        mOldPassword = mUser.getPassword();

        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfNotEmpty()) {
                    Thread t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String mNewPassword = hashPassword(mNewPasswordEdit.getText().toString());
                            cinemaDatabaseService.changePassword(mUsername, mOldPassword, mNewPassword);
                        }
                    });

                    try {
                        t1.start();
                        t1.join();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Password changed", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private boolean checkIfNotEmpty() {
        if (!mOldPasswordEdit.getText().toString().isEmpty() && !mNewPasswordEdit.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    private String hashPassword(String password) {
        String hashedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            hashedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }
}
