package com.rkeeves;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class IntegerCollectionsTests {

    private IntegerCollection underTest;

    @Test
    public void testBubbleSortShouldThrowOnNull() {
        // Given
        underTest = null;

        // When, Then
        assertThrows(NullPointerException.class, () -> IntegerCollections.sort_bubble(underTest));
    }

    @Test
    public void testSortBubbleShouldOrderNoElementCollectionCorrectly() {
        // Given
        underTest = new IntegerCollection(new int[]{ });
        int[] expected = {  };

        // When
        IntegerCollections.sort_bubble(underTest);

        // Then
        assertArrayEquals(expected, underTest.toArray());
    }

    @Test
    public void testSortBubbleShouldOrderOneElementCollectionCorrectly() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1});
        int[] expected = { 1 };

        // When
        IntegerCollections.sort_bubble(underTest);

        // Then
        assertArrayEquals(expected, underTest.toArray());
    }

    @Test
    public void testSortBubbleShouldOrderNElementCollectionCorrectly() {
        // Given
        underTest = new IntegerCollection(new int[]{ 7, 6, 5, 4, 3, 2, 1 });
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };

        // When
        IntegerCollections.sort_bubble(underTest);

        // Then
        assertArrayEquals(expected, underTest.toArray());
    }

    @Test
    public void testShellSortShouldThrowOnNull() {
        // Given
        underTest = null;

        // When, Then
        assertThrows(NullPointerException.class, () -> IntegerCollections.sort_shell(underTest));
    }

    @Test
    public void testSortShellShouldOrderNoElementCollectionCorrectly() {
        // Given
        underTest = new IntegerCollection(new int[]{ });
        int[] expected = {  };

        // When
        IntegerCollections.sort_shell(underTest);

        // Then
        assertArrayEquals(expected, underTest.toArray());
    }

    @Test
    public void testSortShellShouldOrderOneElementCollectionCorrectly() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1 });
        int[] expected = { 1 };

        // When
        IntegerCollections.sort_shell(underTest);

        // Then
        assertArrayEquals(expected, underTest.toArray());
    }

    @Test
    public void testSortShellShouldOrderNElementCollectionCorrectly() {
        // Given
        underTest = new IntegerCollection(new int[]{ 7, 6, 5, 4, 3, 2, 1 });
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };

        // When
        IntegerCollections.sort_shell(underTest);

        // Then
        assertArrayEquals(expected, underTest.toArray());
    }

    @Test
    public void testBinarySearchShouldThrowOnNull() {
        // Given
        underTest = null;

        // When, Then
        assertThrows(NullPointerException.class, () -> IntegerCollections.binary_search(underTest,7));
    }

    @Test
    public void testBinarySearchShouldReturnCorrectIndexWhenValueIsPresent() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2, 3, 4, 5, 6, 7 });
        int expected_index = 4;

        // When
        int actual_index = IntegerCollections.binary_search(underTest, 5);

        // Then
        assertEquals(expected_index,actual_index);
    }

    @Test
    public void testBinarySearchShouldReturnInvalidIndexWhenCollectionEmpty() {
        // Given
        underTest = new IntegerCollection(new int[]{ });
        int expected_index = IntegerCollections.INVALID_INDEX;

        // When
        int actual_index = IntegerCollections.binary_search(underTest, 8);

        // Then
        assertEquals(expected_index,actual_index);
    }

    @Test
    public void testBinarySearchShouldReturnInvalidIndexWhenValueIsNotPresent() {
        // Given
        underTest = new IntegerCollection(new int[]{1, 2, 3, 4, 5, 6, 7});
        int expected_index = IntegerCollections.INVALID_INDEX;

        // When
        int actual_index = IntegerCollections.binary_search(underTest, 8);

        // Then
        assertEquals(expected_index, actual_index);
    }
}
