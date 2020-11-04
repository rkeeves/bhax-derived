package com.rkeeves;

public interface Matrix {

    int getRowCount();

    int getColumnCount();

    int get(int row, int col);

    int set(int row, int col, int val);
}
