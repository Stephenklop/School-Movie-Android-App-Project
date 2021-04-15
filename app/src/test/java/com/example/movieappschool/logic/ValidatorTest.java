package com.example.movieappschool.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    /*
     * @desc Validates if the email address is valid. It should be in the form of:
     * <at least one character>@<at least one character>.<at least one character>
     *
     * @subcontract valid email {
     *  @requires no other precondition
     *  @ensures result = true;
     * }
     */

    // email() method

    @Test
    void testEmailRequiresUserAtEmailDotComEnsuresTrue() {
        // Arrange
        String email = "user@email.com";

        // Act
        final boolean result = Validator.email(email);

        // Assert
        assertTrue(result);
    }

    @Test
    void testEmailRequiresAtEmailDotComEnsuresFalse() {
        // Arrange
        String email = "@email.com";

        // Act
        final boolean result = Validator.email(email);

        // Assert
        assertFalse(result);
    }

    @Test
    void testEmailRequiresEmailDotComEnsuresFalse() {
        // Arrange
        String email = "email.com";

        // Act
        final boolean result = Validator.email(email);

        // Assert
        assertFalse(result);
    }

    @Test
    void testEmailRequiresUserAtDotComEnsuresFalse() {
        // Arrange
        String email = "user@.com";

        // Act
        final boolean result = Validator.email(email);

        // Assert
        assertFalse(result);
    }

    @Test
    void testEmailRequiresUserAtEmailDotEnsuresFalse() {
        // Arrange
        String email = "user@email.";

        // Act
        final boolean result = Validator.email(email);

        // Assert
        assertFalse(result);
    }

    @Test
    void testEmailRequiresAtDotEnsuresFalse() {
        // Arrange
        String email = "@.";

        // Act
        final boolean result = Validator.email(email);

        // Assert
        assertFalse(result);
    }

    // Password must have minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character

    @Test
    void testPasswordRequiresAeTwoPercent4fgaEnsuresTrue() {
        // Arrange
        String password = "Ae2%4fga";

        // Act
        final boolean result = Validator.password(password);

        // Assert
        assertTrue(result);
    }

    @Test
    void testPasswordRequiresEfFourPercentqapdEnsuresFalse() {
        // Arrange
        String password = "ef4%qapd";

        // Act
        final boolean result = Validator.password(password);

        // Assert
        assertFalse(result);
    }

    @Test
    void global() {
    }
}