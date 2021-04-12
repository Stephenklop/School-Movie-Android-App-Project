package com.example.movieappschool.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.MainActivity;
import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.User;

public class AccountActivity extends AppCompatActivity {
    private EditText mUsername, mFirstname, mLastname, mPassword, mEmail, mDateOfBirth, mAddress;
    private Button mUpdate, mChangePassword;
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

        mUpdate = findViewById(R.id.updateButton);
        mChangePassword = findViewById(R.id.changePasswordButton);

        mUsername = findViewById(R.id.my_account_username_input);
        mFirstname = findViewById(R.id.my_account_firstname_input);
        mLastname = findViewById(R.id.my_account_lastname_input);
        //mPassword = findViewById(R.id.my_account_password_input);
        mEmail = findViewById(R.id.my_account_email_input);
        mDateOfBirth = findViewById(R.id.my_account_birthdate_input);
        mAddress = findViewById(R.id.my_account_address_input);

        user = localAppStorage.getUser();

        mUsername.setText(user.getUsername());
        mFirstname.setText(user.getFirstName());
        mLastname.setText(user.getLastName());
        //mPassword.setText(user.getPassword());
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

        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, PasswordActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean checkIfAllFilled() {
        if (!mUsername.getText().toString().isEmpty() || !mFirstname.getText().toString().isEmpty() || !mLastname.getText().toString().isEmpty() || !mEmail.getText().toString().isEmpty() || !mDateOfBirth.getText().toString().isEmpty() ||
                !mAddress.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }
}
