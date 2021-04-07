package com.example.movieappschool.logic;

import java.lang.reflect.Field;

public class ResourceHelper {

    // Find the integer id in a specific resource package based on the id name.
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}