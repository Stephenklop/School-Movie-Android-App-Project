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
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordActivity extends AppCompatActivity {
    private CinemaDatabaseService cinemaDatabaseService;
    private LocalAppStorage localAppStorage;
    private Button mChangePasswordButton;
    private EditText mOldPasswordEdit, mNewPasswordEdit;
    private User mUser;
    private String mUsername, mOldPassword;

    public PasswordActivity() {
        localAppStorage = (LocalAppStorage) this.getApplication();
        cinemaDatabaseService = new CinemaDatabaseService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        mChangePasswordButton = findViewById(R.id.changeButton);
        mOldPasswordEdit = findViewById(R.id.oldPassword);
        mNewPasswordEdit = findViewById(R.id.newPassword);

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
