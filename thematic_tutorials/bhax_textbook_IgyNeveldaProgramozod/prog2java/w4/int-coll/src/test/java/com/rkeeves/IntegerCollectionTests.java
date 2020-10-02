package com.rkeeves;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class IntegerCollectionTests {

    private IntegerCollection underTest;

    @BeforeEach
    public void init() {
        underTest = new IntegerCollection(new int[]{ 7, 6, 5, 4, 3, 2, 1 });
    }

    @Test
    public void testAddShouldAddTheGivenValueToTheCollection() {
        // Given
        underTest = new IntegerCollection(1);
        IntegerCollection expected = new IntegerCollection(new int[] {1});
        IntegerCollection actual = underTest;

        // When
        underTest.add(1);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testAddShouldThrowAnIllegalArgumentExceptionWhenTheCollectionIsFull() {
        // Given
        // When, then
        assertThrows(IllegalArgumentException.class, () -> new IntegerCollection(0));
    }

    @Test
    public void testSortShouldOrderTheIntegersCorrectly() {
        // Given
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };

        // When
        IntegerCollections.sort(underTest);

        // Then
        assertArrayEquals(expected, underTest.toArray());
    }

    @Test
    public void testSortShouldOrderTheIntegersCorrectly_Shell() {
        // Given
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };

        // When
        IntegerCollections.sort_shell(underTest);

        // Then
        assertArrayEquals(expected, underTest.toArray());
    }

    @Test
    public void testContainsShouldReturnTrueWhenItContainsTheGivenValue() {
        // Given

        // When
        boolean actual = underTest.contains(5);

        // Then
        assertTrue(actual);
    }

    @Test
    public void testContainsShouldReturnFalseWhenItDoesNotContainTheGivenValue() {
        // Given

        // When
        boolean actual = underTest.contains(8);

        // Then
        assertFalse(actual);
    }

}
