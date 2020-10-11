package com.rkeeves;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringExample {

    @Test
    public void actuallySameObjectRefsStrings() {
        // Given
        String first = "...";
        String second = "...";
        String third = "...";
        // When
        var firstMatchesSecondWithEquals = first.equals(second);
        assertTrue(firstMatchesSecondWithEquals);
        var firstMatchesSecondWithEqualToOperator = first == second;
        assertTrue(firstMatchesSecondWithEqualToOperator);
        var firstMatchesThirdWithEquals = first.equals(third);
        assertTrue(firstMatchesThirdWithEquals);
        var firstMatchesThirdWithEqualToOperator = first == third;
        assertTrue(firstMatchesThirdWithEqualToOperator);
    }

    @Test
    public void actuallySameObjectRefsStringsWithInternals() {
        // Given
        String first = new String("...").intern();
        String second = new String("...").intern();
        String third = new String("...").intern();
        // When
        var firstMatchesSecondWithEquals = first.equals(second);
        assertTrue(firstMatchesSecondWithEquals);
        var firstMatchesSecondWithEqualToOperator = first == second;
        assertTrue(firstMatchesSecondWithEqualToOperator);
        var firstMatchesThirdWithEquals = first.equals(third);
        assertTrue(firstMatchesThirdWithEquals);
        var firstMatchesThirdWithEqualToOperator = first == third;
        assertTrue(firstMatchesThirdWithEqualToOperator);
    }

    @Test
    public void notTheSameObjectRefsStrings() {
        // Given
        String first = "...";
        String second = "...";
        String third = new String("...");
        // When
        var firstMatchesSecondWithEquals = first.equals(second);
        assertTrue(firstMatchesSecondWithEquals);
        var firstMatchesSecondWithEqualToOperator = first == second;
        assertTrue(firstMatchesSecondWithEqualToOperator);
        var firstMatchesThirdWithEquals = first.equals(third);
        assertTrue(firstMatchesThirdWithEquals);
        var firstMatchesThirdWithEqualToOperator = first == third;
        assertFalse(firstMatchesThirdWithEqualToOperator);
    }

    @Test
    public void actuallySameObjectRefsInts() {
        // Given
        Integer first = 1;
        Integer second = 1;
        Integer third = 1;
        // When
        var firstMatchesSecondWithEquals = first.equals(second);
        assertTrue(firstMatchesSecondWithEquals);
        var firstMatchesSecondWithEqualToOperator = first == second;
        assertTrue(firstMatchesSecondWithEqualToOperator);
        var firstMatchesThirdWithEquals = first.equals(third);
        assertTrue(firstMatchesThirdWithEquals);
        var firstMatchesThirdWithEqualToOperator = first == third;
        assertTrue(firstMatchesThirdWithEqualToOperator);
    }

    @Test
    public void notTheSameObjectRefsInts() {
        // Given
        Integer first = 1;
        Integer second = 1;
        Integer third = new Integer(1);
        // When
        var firstMatchesSecondWithEquals = first.equals(second);
        assertTrue(firstMatchesSecondWithEquals);
        var firstMatchesSecondWithEqualToOperator = first == second;
        assertTrue(firstMatchesSecondWithEqualToOperator);
        var firstMatchesThirdWithEquals = first.equals(third);
        assertTrue(firstMatchesThirdWithEquals);
        var firstMatchesThirdWithEqualToOperator = first == third;
        assertFalse(firstMatchesThirdWithEqualToOperator);
    }
}
