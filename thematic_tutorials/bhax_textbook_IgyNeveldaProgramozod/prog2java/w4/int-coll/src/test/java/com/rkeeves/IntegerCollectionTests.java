package com.rkeeves;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class IntegerCollectionTests {

    private IntegerCollection underTest;

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
    public void testAddShouldThrowAnIllegalArgumentExceptionWhenCtorIsCalledWithZeroCapacity() {
        // Given
        underTest = new IntegerCollection(new int[]{ 7, 6, 5, 4, 3, 2, 1 });

        // When, then
        assertThrows(IllegalArgumentException.class, () -> new IntegerCollection(0));
    }

    @Test
    public void testContainsShouldReturnTrueWhenItContainsTheGivenValue() {
        // Given
        underTest = new IntegerCollection(new int[]{ 7, 6, 5, 4, 3, 2, 1 });

        // When
        boolean actual = underTest.contains(5);

        // Then
        assertTrue(actual);
    }

    @Test
    public void testContainsShouldReturnFalseWhenItDoesNotContainTheGivenValue() {
        // Given
        underTest = new IntegerCollection(new int[]{ 7, 6, 5, 4, 3, 2, 1 });

        // When
        boolean actual = underTest.contains(8);

        // Then
        assertFalse(actual);
    }

    @Test
    public void testShouldIncreaseCapacityIfFullWhenAdd() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1 });
        int expected_capacity = 2 * underTest.getCapacity();

        // When
        underTest.add(2);

        // Then
        assertEquals(expected_capacity, underTest.getCapacity());
    }

    @Test
    public void testShouldGetValueIfPreset() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 });
        int asked_index = 0;
        int expected_value = 1;

        // When
        int actual_value = underTest.get(asked_index);

        // Then
        assertEquals(expected_value, actual_value);
    }

    @Test
    public void testShouldThrowWhenGetNegativeIndex() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 });

        // When, then
        assertThrows(IndexOutOfBoundsException.class, () -> underTest.get(-1));
    }

    @Test
    public void testShouldThrowWhenGetIndexNotSmallerThanSize() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 });
        int asked_index = underTest.getSize();

        // When, then
        assertThrows(IndexOutOfBoundsException.class, () -> underTest.get(asked_index));
    }

    @Test
    public void testShouldSetValueIfIndexISValid() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 });
        int asked_index = 0;
        int expected_value = 5;

        // When
        underTest.set(asked_index,expected_value);

        // Then
        assertEquals(expected_value, underTest.get(asked_index));
    }

    @Test
    public void testShouldThrowWhenSetNegativeIndex() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 });

        // When, then
        assertThrows(IndexOutOfBoundsException.class, () -> underTest.set(-1,1));
    }

    @Test
    public void testShouldThrowWhenSetIndexNotSmallerThanSize() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 });
        int asked_index = underTest.getSize();

        // When, then
        assertThrows(IndexOutOfBoundsException.class, () -> underTest.set(asked_index,1));
    }

    @Test
    public void testShouldThrowWhenSwapFirstArgIsInvalid() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 });

        // When, then
        assertThrows(IndexOutOfBoundsException.class, () -> underTest.swap(-1,0));
    }

    @Test
    public void testShouldThrowWhenSwapSecondArgIsInvalid() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 });

        // When, then
        assertThrows(IndexOutOfBoundsException.class, () -> underTest.swap(0,-1));
    }

    @Test
    public void testShouldThrowWhenSwapFirstArgIsNotSmallerThanSize() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 });

        // When, then
        assertThrows(IndexOutOfBoundsException.class, () -> underTest.swap(underTest.getSize(),0));
    }

    @Test
    public void testShouldThrowWhenSwapSecondArgIsNotSmallerThanSize() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 });

        // When, then
        assertThrows(IndexOutOfBoundsException.class, () -> underTest.swap(0,underTest.getSize()));
    }

    @Test
    public void testShouldSwapCorrectly() {
        // Given
        underTest = new IntegerCollection(new int[]{ 1, 2 , 3});
        int idx_a = 0;
        int idx_b = 2;
        underTest.swap(idx_a,idx_b);
        // When, then
        assertEquals(3,underTest.get(0));
        assertEquals(2,underTest.get(1));
        assertEquals(1,underTest.get(2));
    }

}
