package com.example.movieappschool.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;

public class RegisterActivity extends AppCompatActivity {
    private CinemaDatabaseService cinemaDatabaseService;
    private EditText mUsername, mFirstname, mLastname, mPassword, mEmail, mAddress, mDateBirth;
    private Button mCreateAccount;

    public RegisterActivity() {
        cinemaDatabaseService = new CinemaDatabaseService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mUsername = findViewById(R.id.register_username_input);
        mFirstname = findViewById(R.id.register_firstname_input);
        mLastname = findViewById(R.id.register_lastname_input);
        mPassword = findViewById(R.id.register_password_input);
        mEmail = findViewById(R.id.register_email_input);
        mAddress = findViewById(R.id.register_address_input);
        mDateBirth = findViewById(R.id.register_birthdate_input);
        mCreateAccount = findViewById(R.id.register_submit_button);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String firstname = mFirstname.getText().toString();
                String lastname = mLastname.getText().toString();
                String password = mPassword.getText().toString();
                String email = mEmail.getText().toString();
                String address = mAddress.getText().toString();
                String datebirth = mDateBirth.getText().toString();

                if(checkIfFilled(username, firstname, lastname, password, email, address, datebirth)) {
                    Thread t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            cinemaDatabaseService.createAccount(username, firstname, lastname, password, email, address, datebirth);

                        }
                    });

                    try {
                        t1.start();
                        t1.join();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Account created", Toast.LENGTH_LONG);
                    toast.show();

                }
            }
        });
    }

    public boolean checkIfFilled(String username, String firstname, String lastname, String password, String email, String address, String datebirth) {
        if (!username.isEmpty() && !firstname.isEmpty() && !lastname.isEmpty() && !password.isEmpty() && !email.isEmpty() && !address.isEmpty() && !datebirth.isEmpty()) {
            return true;
        }
        return false;
    }
}
