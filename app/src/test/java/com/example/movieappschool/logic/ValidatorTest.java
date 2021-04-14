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

    @Test
    void testEmailRequiresUserAtEmailDotComEnsuresTrue() {
        // Arrange
        String email = "user@email.com";

        // Act
        final boolean result = Validator.email(email);

        // Assert
        assertTrue(result);
        // assertEquals() also works of course
    }

    @Test
    void password() {
    }

    @Test
    void global() {
    }
}