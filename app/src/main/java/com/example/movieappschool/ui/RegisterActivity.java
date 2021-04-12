package com.example.movieappschool.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;
import com.example.movieappschool.data.CinemaDatabaseService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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



                if(validate()) {
                    Thread t1 = new Thread(new Runnable() {
                        @Override
                        public void run(){
                            String hashedPassword = hashPassword(password);

                            System.out.println("Hashed password: " + hashedPassword);
                            cinemaDatabaseService.createAccount(username, firstname, lastname, hashedPassword, email, address, datebirth);
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

    private boolean validate(){
        boolean valid = true;

        if (mUsername.getText().toString().isEmpty()){
            mUsername.setError("controleer gebruikersnaam");
            valid = false;
        }

        if (mFirstname.getText().toString().isEmpty()){
            mFirstname.setError("controleer voornaam");
            valid = false;
        }

        if (mLastname.getText().toString().isEmpty()){
            mLastname.setError("controleer achternaam");
            valid = false;
        }

        //email must be like user@domain.tld
        if (!mEmail.getText().toString().matches("^(.+)@(.+)$")){
            mEmail.setError("controleer email");
            valid = false;
        }
        //mPassword must have minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
        if (!mPassword.getText().toString().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")){
            mPassword.setError("Wachtwoord moet minimaal uit 8 tekens bestaan waarvan 1 hoofdletter, 1 special character en 1 cijfer");
            valid = false;
        }

        //mDateBirth is allowed to be dd/mm/yyyy
        if (!mDateBirth.getText().toString().matches("(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)\n")){
            mDateBirth.setError("Formaat geboortedatum moeten 01/01/2000 zijn");
            valid = false;
        }

        if (mAddress.getText().toString().isEmpty()){
            mAddress.setError("controleer Adres");
            valid = false;
        }

        if (!valid){
            return false;
        } else {
            return true;
        }

    }




}
