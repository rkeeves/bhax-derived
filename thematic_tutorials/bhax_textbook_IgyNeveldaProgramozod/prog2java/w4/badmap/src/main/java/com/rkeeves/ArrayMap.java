package com.rkeeves;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Custom Map implementation without using Java Collections.
 * This implementation does not allow null keys and
 * the keySet, values, entrySet methods does not support element removal.
 *
 * @param <K> key type parameter
 * @param <V> value type parameter
 */
public class ArrayMap<K, V> implements Map<K, V> {

    private static final int INITIAL_SIZE = 16;

    private Vec<Pair> pairs;

    public ArrayMap() {
        pairs = new Vec<Pair>();
    }

    class Pair implements Entry<K,V>, Cloneable{
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        public Pair copy(){
            return new Pair(key,value);
        }
    }

    @Override
    public int size() {
        return pairs.size();
    }

    @Override
    public boolean isEmpty() {
        return pairs.size() <= 0;
    }

    private boolean isKeyMatching(Object searchedKey, Pair p){
        if(searchedKey == null){
          return (p.getKey() == null);
        }else{
            return searchedKey.equals(p.getKey());
        }
    }

    private boolean isValueMatching(Object searchedValue, Pair p){
        if(searchedValue == null){
            return (p == null);
        }else{
            return searchedValue.equals(p.getKey());
        }
    }

    @Override
    public boolean containsKey(Object key) {
        return pairs.findFirstMatching(key,this::isKeyMatching) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return pairs.findFirstMatching(value,this::isValueMatching) != null;
    }

    @Override
    public V get(Object key) {
        Pair p = pairs.findFirstMatching(key,this::isKeyMatching);
        return (p==null) ? null : p.getValue();
    }

    @Override
    public V put(K key, V value) {
        Pair p = pairs.findFirstMatching(key,this::isKeyMatching);
        V lastValue;
        if(p == null){
            lastValue = null;
            pairs.add(new Pair(key,value));
        }else{
            lastValue = p.getValue();
            p.setValue(value);
        }
        return lastValue;
    }

    @Override
    public V remove(Object key) {
        int i = pairs.indexOfFirstMatching(key,this::isKeyMatching);
        if(i != Vec.END_INDEX){
            V oldValue = pairs.get(i).getValue();
            pairs.remove(i);
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        pairs.clear();
    }

    @Override
    public Set<K> keySet() {
        Set<K> res = new HashSet<>();
        for (Pair p : pairs) {
            if(p!=null){
                res.add(p.getKey());
            }
        }
        return res;
    }

    @Override
    public Collection<V> values() {
        List<V> res = new LinkedList<>();
        for (Pair p : pairs) {
            if(p!=null){
                res.add(p.getValue());
            }
        }
        return res;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> res = new HashSet<>();
        for (Pair p : pairs) {
            if(p!=null){
                res.add(p);
            }
        }
        return res;
    }
}
