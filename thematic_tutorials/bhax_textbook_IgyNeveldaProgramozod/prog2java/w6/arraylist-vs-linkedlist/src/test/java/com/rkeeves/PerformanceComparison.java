package com.rkeeves;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerformanceComparison {

    private static class Clock{

        long current = System.nanoTime();

        long elapsedSinceLast(){
            long last = current;
            current =  System.nanoTime();
            return current - last;
        }
    }

    @Test
    void insertFront_LinkedListFaster(){
        int number_of_insertions = 1000;
        Clock clock = new Clock();

        List<Integer> list = new ArrayList<>();
        clock.elapsedSinceLast();
        insertFront(list, 0,number_of_insertions);
        long arrayListInsertionTime = clock.elapsedSinceLast();

        list = new LinkedList<>();
        clock.elapsedSinceLast();
        insertFront(list, 0,number_of_insertions);
        long linkedListInsertionTime = clock.elapsedSinceLast();

        assertTrue(arrayListInsertionTime > linkedListInsertionTime);
    }

    private <T> void insertFront(List<T> list, T element, int n){
        for (int i = 0; i < n; i++) {
            list.add(0, element);
        }
    }

    @Test
    void removeFirst_LinkedListFaster(){
        int number_of_insertions = 1000;
        Clock clock = new Clock();

        List<Integer> list = new ArrayList<>();
        insertFront(list, 0,number_of_insertions);
        clock.elapsedSinceLast();
        removeFirst(list);
        long arrayListInsertionTime = clock.elapsedSinceLast();

        list = new LinkedList<>();
        insertFront(list, 0,number_of_insertions);
        clock.elapsedSinceLast();
        removeFirst(list);
        long linkedListInsertionTime = clock.elapsedSinceLast();

        assertTrue(arrayListInsertionTime > linkedListInsertionTime);
    }

    private <T> void removeFirst(List<T> list){
        while(!list.isEmpty()){
            list.remove(0);
        }
    }

    @Test
    void randomAccess_ArrayListFaster(){
        int number_of_insertions = 1000;
        Clock clock = new Clock();

        List<Integer> list = new ArrayList<>();
        insertFront(list, 0,number_of_insertions);
        clock.elapsedSinceLast();
        accessAll(list);
        long arrayListInsertionTime = clock.elapsedSinceLast();

        list = new LinkedList<>();
        insertFront(list, 0,number_of_insertions);
        clock.elapsedSinceLast();
        accessAll(list);
        long linkedListInsertionTime = clock.elapsedSinceLast();

        assertTrue(arrayListInsertionTime < linkedListInsertionTime);
    }



    private <T> void accessAll(List<T> list){
        for (T v : list) {

        }
    }
}
