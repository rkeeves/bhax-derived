package com.rkeeves;

import java.util.stream.IntStream;

public class MatrixStreamMultiplication {

    public static Matrix multiply(Matrix multiplied, Matrix multiplier) {
        if (multiplied.getColumnCount() != multiplier.getRowCount()) {
            throw new RuntimeException("Matrices were of incompatible dimensions");
        }
        Matrix result = new BasicMatrix(multiplied.getRowCount(), multiplier.getColumnCount());
        IntStream.range(0, result.getRowCount())
                .forEach((i) -> IntStream.range(0, result.getColumnCount())
                        .forEach(j ->
                                result.set(i, j,
                                        IntStream.range(0, multiplied.getColumnCount())
                                                .reduce(0, (accu, n) -> accu + (multiplied.get(i, n) * multiplier.get(n, j))))));
        return result;
    }


    public static int[][] multiplyRawArray(int[][] multiplied, int[][] multiplier) {
        int multipliedRowCount = multiplied.length;
        int multipliedColumnCount = (multiplied.length > 0) ? multiplied[0].length : 0;
        int multiplierRowCount = multiplier.length;
        int multiplierColumnCount = (multiplier.length > 0) ? multiplier[0].length : 0;
        ;
        if (multipliedColumnCount != multiplierRowCount) {
            throw new RuntimeException("Matrices were of incompatible dimensions");
        }
        int resultRowCount = multipliedRowCount;
        int resultColumnCount = multiplierColumnCount;
        if (multipliedColumnCount == 0 || resultRowCount == 0 || resultColumnCount == 0) {
            throw new RuntimeException("One of the  dimensions was 0");
        }

        int[][] res =
                IntStream.range(0, resultRowCount)
                        .mapToObj(i -> IntStream.range(0, resultColumnCount)
                                .map(j -> IntStream.range(0, multipliedColumnCount)
                                        .map(k -> multiplied[i][k] * multiplier[k][j])
                                        .sum()).toArray()).toArray(int[][]::new);
        return res;
    }
}
