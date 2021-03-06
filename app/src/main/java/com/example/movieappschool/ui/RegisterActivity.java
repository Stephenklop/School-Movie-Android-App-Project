package com.example.movieappschool.ui;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.logic.Validator;
import com.example.movieappschool.ui.menu.MenuActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterActivity extends AppCompatActivity {
    private CinemaDatabaseService cinemaDatabaseService;
    private EditText mUsername, mFirstname, mLastname, mPassword, mEmail, mAddress, mDateBirth;
    private Button mCreateAccount;
    private String dateBirthFinal;

    public RegisterActivity() {
        cinemaDatabaseService = new CinemaDatabaseService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Menu
        View toolBar = findViewById(R.id.register_toolbar);
        ImageView hamburgerIcon = toolBar.findViewById(R.id.hamburger_icon);

        hamburgerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("prevActivity", getClass().getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

            getApplicationContext().startActivity(intent, options.toBundle());
        });


        mUsername = findViewById(R.id.register_username_input);
        mFirstname = findViewById(R.id.register_firstname_input);
        mLastname = findViewById(R.id.register_lastname_input);
        mPassword = findViewById(R.id.register_password_input);
        mEmail = findViewById(R.id.register_email_input);
        mAddress = findViewById(R.id.register_address_input);
        mDateBirth = findViewById(R.id.register_birthdate_input);
        mCreateAccount = findViewById(R.id.register_submit_button);

        mDateBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDatePicker();
            }
        });

        mCreateAccount.setOnClickListener(v -> {
            String username = mUsername.getText().toString();
            String firstname = mFirstname.getText().toString();
            String lastname = mLastname.getText().toString();
            String password = mPassword.getText().toString();
            String email = mEmail.getText().toString();
            String address = mAddress.getText().toString();
            String datebirth = mDateBirth.getText().toString();
            
            AtomicBoolean errorOccurred = new AtomicBoolean(false);

            if (validate()) {
                Thread t1 = new Thread(() -> {
                    String hashedPassword = hashPassword(password);

                    try {
                        cinemaDatabaseService.createAccount(username, firstname, lastname, hashedPassword, email, address, dateBirthFinal);

                        emptyInputs();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        errorOccurred.set(true);
                    }
                });

                try {
                    t1.start();
                    t1.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Context context = getApplicationContext();

                if (errorOccurred.get()) {
                    Toast.makeText(context, getResources().getString(R.string.register_username_email_error), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.register_account_created_notification), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setDatePicker() {
        int mYear, mMonth, mDay, mHour, mMinute;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mDateBirth.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                dateBirthFinal = year + "-" + (month + 1) + "-" + dayOfMonth;
                mDateBirth.setError(null);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
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

    private boolean validate() {
        boolean valid = true;

        if (!Validator.global(mUsername.getText().toString())) {
            mUsername.setError(getResources().getString(R.string.textfield_wrong));
            valid = false;
        }

        if (!Validator.global(mFirstname.getText().toString())) {
            mFirstname.setError(getResources().getString(R.string.textfield_wrong));
            valid = false;
        }

        if (!Validator.global(mLastname.getText().toString())) {
            mLastname.setError(getResources().getString(R.string.textfield_wrong));
            valid = false;
        }

        if (!Validator.email(mEmail.getText().toString())) {
            mEmail.setError(getResources().getString(R.string.textfield_wrong));
            valid = false;
        }

        if (!Validator.password(mPassword.getText().toString())) {
            mPassword.setError(getResources().getString(R.string.password_requirements));
            valid = false;
        }


        if (!Validator.global(mDateBirth.getText().toString())) {
            mDateBirth.setError(getResources().getString(R.string.textfield_wrong));
            valid = false;
        }

        if (!Validator.global(mAddress.getText().toString())) {
            mAddress.setError(getResources().getString(R.string.textfield_wrong));
            valid = false;
        }

        if (!valid) {
            return false;
        } else {
            return true;
        }

    }

    private void emptyInputs() {
        mUsername.getText().clear();
        mFirstname.getText().clear();
        mLastname.getText().clear();
        mPassword.getText().clear();
        mEmail.getText().clear();
        mAddress.getText().clear();
        mDateBirth.getText().clear();
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
}
