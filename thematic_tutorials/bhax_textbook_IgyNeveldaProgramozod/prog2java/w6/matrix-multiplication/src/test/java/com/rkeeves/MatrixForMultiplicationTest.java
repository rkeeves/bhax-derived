package com.rkeeves;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixForMultiplicationTest {

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
    void multiply_EPAMDataSet() {
        Matrix result = MatrixForMultiplication.multiply(matrixA,matrixB);
        assertEquals(expected, result);
    }
}
