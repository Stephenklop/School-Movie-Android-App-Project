package com.example.movieappschool.logic;

public class validator {

    //email must be like user@domain.tld
    public static boolean email(String emailaddress) {
        if (!emailaddress.matches("^(.+)@(.+)$")) {
            return false;
        } else {
            return true;
        }

    }

    //mPassword must have minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
    public static boolean password(String password) {
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean global(String input) {
        if (input.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }
}
