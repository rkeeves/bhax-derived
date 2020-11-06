package com.rkeeves.refactored;

import java.util.Iterator;

public class NatSet implements Iterable<Integer>{

    private static final class NatIterator implements Iterator<Integer>{

        private int n = 0;
        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            return n++;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new NatIterator();
    }
}
