package com.rkeeves;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixStreamMultiplicationTest {

    int[][] matrixA = new int[][] {
            {1, 5},
            {2, 3},
            {1, 7}};

    int[][] matrixB = new int[][] {
            {1, 2, 3, 7},
            {5, 2, 8, 1}};

    int[][] expected = new int[][] {
            {26, 12, 43, 12},
            {17, 10, 30, 17},
            {36, 16, 59, 14}};

    @Test
    void multiply_BasicMatrixOverload_EPAMDataSet() {
        Matrix result = MatrixStreamMultiplication.multiply(new BasicMatrix(matrixA),new BasicMatrix(matrixB));
        assertEquals(new BasicMatrix(expected), result);
    }

    @Test
    void multiplyRawArray_EPAMDataSet() {
        var result = MatrixStreamMultiplication.multiplyRawArray(matrixA,matrixB);
        assertEquals(new BasicMatrix(expected), new BasicMatrix(result));
    }
}
