package com.example.movieappschool.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

        // Set logout receiver
        setLogoutReceiver();

        Intent intent = getIntent();
        previousActivity = intent.getStringExtra("prevActivity");

        System.out.println("Prev: " + previousActivity);

        toolbar = findViewById(R.id.change_password_toolbar);
        toolbar.findViewById(R.id.hamburger_icon).setVisibility(View.INVISIBLE);

        backButton = toolbar.findViewById(R.id.back_icon);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(v -> {
            Intent backIntent = new Intent(getApplicationContext(), AccountActivity.class);
            backIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            getApplicationContext().startActivity(backIntent);
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
                    Toast.makeText(context, getResources().getString(R.string.change_password_password_changed_notification), Toast.LENGTH_LONG).show();
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

    private void setLogoutReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                finish();
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        }, intentFilter);
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
