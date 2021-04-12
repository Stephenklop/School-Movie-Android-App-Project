package com.example.movieappschool.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.data.LoginService;
import com.example.movieappschool.domain.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity{
    LoginService login = new LoginService();
    private LocalAppStorage localAppStorage;
    private Button mLoginButton, mRegisterButton;
    private EditText mUsernameInput, mPasswordInput;
    private User mUser;

    public LoginActivity() {
        localAppStorage = (LocalAppStorage) this.getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mLoginButton = findViewById(R.id.loginButton);
        mRegisterButton = findViewById(R.id.registerButton);
        mUsernameInput = findViewById(R.id.oldPassword);
        mPasswordInput = findViewById(R.id.newPassword);

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String Username = mUsernameInput.getText().toString();
                String Password = mPasswordInput.getText().toString();
                
                String hashedPassword = hashPassword(Password);

                mUser = login.executeLogin(Username, hashedPassword);
                if (mUser != null) {
                    localAppStorage.setUser(mUser);
                    localAppStorage.setLoggedIn();
                    Intent i = new Intent(LoginActivity.this, AccountActivity.class);
                    startActivity(i);
                }
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
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
