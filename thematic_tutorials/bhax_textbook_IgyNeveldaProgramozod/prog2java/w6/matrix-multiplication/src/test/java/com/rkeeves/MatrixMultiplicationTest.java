package com.rkeeves;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixMultiplicationTest {

    Matrix matrixA = new BasicMatrix(new int[][] {
            {1, 5},
            {2, 3},
            {1, 7}});
    Matrix matrixB = new BasicMatrix(new int[][] {
            {1, 2, 3, 7},
            {5, 2, 8, 1}});
    Matrix expected = new BasicMatrix(new int[][] {
            {26, 12, 43, 12},
            {17, 10, 30, 17},
            {36, 16, 59, 14}});

    @Test
    void testForLoopBased() {
        Matrix result = MatrixForMultiplication.multiply(matrixA,matrixB);
        assertEquals(expected, result);
    }

    @Test
    void testStreamBased() {
        Matrix result = MatrixStreamMultiplication.multiply(matrixA,matrixB);
        assertEquals(expected, result);
    }
}
