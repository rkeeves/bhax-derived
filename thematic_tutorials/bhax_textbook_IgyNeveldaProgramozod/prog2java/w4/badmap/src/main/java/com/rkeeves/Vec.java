package com.rkeeves;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@EqualsAndHashCode
@ToString
public class Vec<T> implements Iterable<T>{

    public static final int INITIAL_CAPACITY = 4;

    public static final int END_INDEX = -1;

    T[] arr;

    int index = 0;

    @Getter
    int size;

    @Getter
    int capacity;

    public Vec() {
        this.size=0;
        this.capacity=INITIAL_CAPACITY;
        this.arr=(T[]) new Object[capacity];
    }

    public int size(){
        return size;
    }

    public void add(T v) {
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

    public void clear(){
        size = 0;
    }

    public T get(int idx) {
        check_range(idx);
        return arr[idx];
    }

    public T set(int idx, T v) {
        check_range(idx);
        T t = arr[idx];
        arr[idx] = v;
        return t;
    }

    private void check_range(int idx){
        if(idx >= size || idx < 0){
            throw new IndexOutOfBoundsException("Tried to access index "+ idx+ " of a Vec which had size "+size);
        }
    }

    public void remove(int idx) {
        check_range(idx);
        if(idx<size-1){
            for (int i = idx+1;i<size;++i){
                arr[i] = arr[i+1];
            }
        }

        size--;
    }

    class VecIterator implements Iterator<T>{
        private int idx = -1;

        @Override
        public boolean hasNext() {
            return (idx+1) < size();
        }

        @Override
        public T next() {
            return get(++idx);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new VecIterator();
    }

    public <I>  T findFirstMatching(I item, BiPredicate<I,T> pred){
        for(T t : this){
            if(pred.test(item,t))
                return  t;
        }
        return null;
    }

    public <I>  int indexOfFirstMatching(I item, BiPredicate<I,T> pred){
        for(int i = 0; i<this.size; ++i){
            if(pred.test(item, arr[i]))
                return i;
        }
        return END_INDEX;
    }
}