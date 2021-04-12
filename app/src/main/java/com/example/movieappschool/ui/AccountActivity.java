package com.example.movieappschool.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.MainActivity;
import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.User;
import com.example.movieappschool.ui.menu.MenuActivity;

public class AccountActivity extends AppCompatActivity {
    private EditText mUsername, mFirstname, mLastname, mPassword, mEmail, mDateOfBirth, mAddress;
    private Button mUpdate;
    private User user;
    private LocalAppStorage localAppStorage;
    private CinemaDatabaseService cinemaDatabaseService;

    public AccountActivity() {
        localAppStorage = (LocalAppStorage) this.getApplication();
        cinemaDatabaseService = new CinemaDatabaseService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        // Menu
        View toolBar = findViewById(R.id.my_account_toolbar);
        ImageView hamburgerIcon = toolBar.findViewById(R.id.hamburger_icon);

        hamburgerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("prevActivity", getClass().getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

            getApplicationContext().startActivity(intent, options.toBundle());
        });

        mUpdate = findViewById(R.id.updateButton);

        mUsername = findViewById(R.id.my_account_username_input);
        mFirstname = findViewById(R.id.my_account_firstname_input);
        mLastname = findViewById(R.id.my_account_lastname_input);
        mPassword = findViewById(R.id.my_account_password_input);
        mEmail = findViewById(R.id.my_account_email_input);
        mDateOfBirth = findViewById(R.id.my_account_birthdate_input);
        mAddress = findViewById(R.id.my_account_address_input);

        user = localAppStorage.getUser();

        mUsername.setText(user.getUsername());
        mFirstname.setText(user.getFirstName());
        mLastname.setText(user.getLastName());
        mPassword.setText("********");
        mEmail.setText(user.getEmail());
        mDateOfBirth.setText(user.getDateBirth());
        mAddress.setText(user.getAddress());

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIfAllFilled()) {
                    Thread t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            user = cinemaDatabaseService.updateUser(user.getUserId(), mFirstname.getText().toString(), mLastname.getText().toString(), mUsername.getText().toString(), mAddress.getText().toString(),
                                    mEmail.getText().toString(), user.getPassword(), mDateOfBirth.getText().toString());
                            localAppStorage.deleteUser();
                            localAppStorage.setUser(user);

                        }
                    });

                    try {
                        t1.start();
                        t1.join();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Account details updated", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        mPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, PasswordActivity.class);
                startActivity(i);
            }
        });
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

    private boolean checkIfAllFilled() {
        if (!mUsername.getText().toString().isEmpty() || !mFirstname.getText().toString().isEmpty() || !mLastname.getText().toString().isEmpty() || !mEmail.getText().toString().isEmpty() || !mDateOfBirth.getText().toString().isEmpty() ||
                !mAddress.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }
}
