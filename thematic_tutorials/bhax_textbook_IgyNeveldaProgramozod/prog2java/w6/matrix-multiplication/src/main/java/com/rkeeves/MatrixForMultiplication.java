package com.rkeeves;

public class MatrixForMultiplication {

    public static Matrix multiply(Matrix multiplied, Matrix multiplier){
        if(multiplied.getColumnCount() != multiplier.getRowCount()){
            throw new RuntimeException("Matrices were of incompatible dimensions");
        }
        Matrix result = new BasicMatrix(multiplied.getRowCount(),multiplier.getColumnCount());
        for (int i = 0; i < result.getRowCount(); i++) {
            for (int j = 0; j < result.getColumnCount(); j++) {
                result.set(i,j, resultAt(multiplied,multiplier,i,j));
            }
        }
        return result;
    }

    public static int resultAt(Matrix a, Matrix b, int i, int j){
        int n = a.getColumnCount();
        int accu = 0;
        for (int c = 0;c < n; ++c)
            accu += a.get(i,c) * b.get(c,j);
        return accu;
    }
}
