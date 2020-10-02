package com.rkeeves;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@EqualsAndHashCode
@ToString
public class IntegerCollection {

    int[] arr;

    int index = 0;

    @Getter
    int size;

    @Getter
    int capacity;

    public IntegerCollection(int capacity) {
        if(capacity < 1){
            throw new IllegalArgumentException("IntegerCollection constructor got argument capacity "+capacity+" which is less than 1(minimal acceptable value)");
        }
        this.size=0;
        this.capacity=capacity;
        this.arr=new int[capacity];
    }

    public IntegerCollection(int[] array) {
        if(array == null){
            this.size=0;
            this.capacity=1;
            this.arr= new int[1];
        }else{
            this.size=array.length;
            this.capacity=this.size;
            this.arr=Arrays.copyOf(array,this.size);
        }
    }

    public void add(int v) {
        if (size >= capacity){
            grow();
        }
        arr[size++] = v;
    }

    private void grow(){
        int increased_capacity = (capacity > 0) ? 2 * capacity : 1;
        this.arr = Arrays.copyOf(this.arr, increased_capacity);
        this.capacity = increased_capacity;
    }

    public boolean contains(int v) {
        for (int i = 0; i < size; i++) {
            if(arr[i] == v)
                return true;
        }
        return false;
    }

    public int get(int idx) {
        check_range(idx);
        return arr[idx];
    }

    public int set(int idx, int v) {
        check_range(idx);
        int t = arr[idx];
        arr[idx] = v;
        return t;
    }

    public void swap(int idx_a, int idx_b) {
        check_range(idx_a);
        check_range(idx_b);
        int t = arr[idx_a];
        arr[idx_a] = arr[idx_b];
        arr[idx_b] = t;
    }

    private void check_range(int idx){
        if(idx >= size || idx < 0){
            throw new IndexOutOfBoundsException("Tried to access index "+ idx+ " of a IntegerCollection which had size "+size);
        }
    }

    public int[] toArray(){
        return Arrays.copyOf(arr,size);
    }
}
