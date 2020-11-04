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
}
