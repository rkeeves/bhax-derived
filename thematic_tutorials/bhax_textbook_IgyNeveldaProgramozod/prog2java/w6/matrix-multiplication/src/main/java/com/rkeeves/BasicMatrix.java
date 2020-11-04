package com.rkeeves;

import lombok.Data;

@Data
public class BasicMatrix implements Matrix{

    private final int rowCount;

    private final int columnCount;

    private final int[][] array;

    public BasicMatrix(int[][] array) {
        this.rowCount = array.length;
        this.columnCount = array[0].length;
        this.array=array;
    }

    public BasicMatrix(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.array = new int[rowCount][];
        for (int r = 0; r < rowCount; r++) {
            array[r] = new int[columnCount];
        }
    }

    @Override
    public int get(int row, int col) {
        return array[row][col];
    }

    @Override
    public int set(int row, int col, int val) {
        int t =  array[row][col];
        array[row][col] = val;
        return t;
    }
}
