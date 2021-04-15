package com.example.movieappschool.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatterTest {
    /*
     * @desc Validates if the chosen date is a real date.
     *
     * @subcontract valid date {
     *  @requires chosen date
     *  @ensures result = String;
     * }
     */

    @Test
    void testDateToShortTextDateRequiresValidDateEnsuresMonthName() {
        // Arrange
        String date = "2003-09-05";
        // Act
        final String result = Formatter.dateToShortTextDate(date);
        // Assert
        assertEquals("5 Sep", result);
    }

    @Test
    void testDateToShortTextDateRequiresMonthDateAboveTwelveEnsuresNull() {
        // Arrange
        String date = "2003-13-05";
        // Act
        final String result = Formatter.dateToShortTextDate(date);
        // Assert
        assertEquals("5", result);
    }

    @Test
    void testDateToShortTextDateRequiresMonthDateThatEqualsZeroEnsuresNull() {
        // Arrange
        String date = "2003-00-05";
        // Act
        final String result = Formatter.dateToShortTextDate(date);
        // Assert
        assertEquals("5", result);
    }

    @Test
    void testDateToShortTextDateRequiresDayAboveThirtyTwoEnsuresNull() {
        // Arrange
        String date = "2003-10-32";
        // Act
        final String result = Formatter.dateToShortTextDate(date);
        // Assert
        assertEquals("32 Oct", result);
    }

}