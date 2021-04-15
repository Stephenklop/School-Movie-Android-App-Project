package com.example.movieappschool.logic;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieappschool.R;

import org.junit.Test;

import static com.example.movieappschool.logic.ResourceHelper.getResId;
import static org.junit.Assert.*;

public class ResourceHelperTest {
    /*
     * @desc Validates if the email address is valid. It should be in the form of:
     * <at least one character>@<at least one character>.<at least one character>
     *
     * @subcontract valid getResID {
     *  @requires no other precondition
     *  @ensures result = true;
     * }
     *
     * @desc
     *
     */

    // getResId() method

    @Test
    void testGetResId() {
        // Arrange
        String resName = "";

        // Act;
        final boolean result = ResourceHelper.getResId(resName, );

        // Assert
        assertTrue(result);
    }

}


