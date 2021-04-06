package com.example.movieappschool.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.data.UserDAO;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsernameInput, mPasswordInput;
    private Button mLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        UserDAO mUserDAO = new UserDAO();
        mUsernameInput = findViewById(R.id.editUsername);
        mPasswordInput = findViewById(R.id.editPassword);
        mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = mUsernameInput.getText().toString();
                String mPassword = mPasswordInput.getText().toString();

                if (checkIfEmpty(mUsernameInput, mPasswordInput)) {
                    // Error geven dat een van de twee velden niet is ingevoerd
                } else {
                    if (mUserDAO.doesUserExist(mUsername, mPassword)) {
                        //Gebruiker wordt ingelogd
                    } else {
                        // Error geven dat combinatie gebruikersnaam en wachtwoord niet correct is
                    }
                }
            }
        });
    }

    public boolean checkIfEmpty(EditText mUsernameInput, EditText mPasswordInput) {
        if (TextUtils.isEmpty(mUsernameInput.getText()) || TextUtils.isEmpty(mPasswordInput.getText())) {
            return true;
        }
        return false;
    }
}
