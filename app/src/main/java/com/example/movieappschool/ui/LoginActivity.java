package com.example.movieappschool.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.data.LoginService;
import com.example.movieappschool.data.UserDAO;

public class LoginActivity extends AppCompatActivity {
    LoginService login = new LoginService();
    private Button mLoginButton;
    private EditText mUsernameInput, mPasswordInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mLoginButton = findViewById(R.id.loginButton);
        mUsernameInput = findViewById(R.id.editUsername);
        mPasswordInput = findViewById(R.id.editPassword);

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Insert values into variables
                String Username = mUsernameInput.getText().toString();
                String Password = mPasswordInput.getText().toString();

                login.executeLogin(Username, Password);
            }
        });
    }

}
