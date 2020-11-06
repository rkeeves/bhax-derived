package com.rkeeves;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScopeTest {

    @Test
    void test(){
        BiFunction<Integer,Integer,Function<BiFunction<Integer,Integer,Integer>,Integer>> make_pair = (a,b)->f->f.apply(a,b);
        Function<Function<BiFunction<Integer,Integer,Integer>,Integer>,Integer> first = f->f.apply((a,b)->a);
        Function<Function<BiFunction<Integer,Integer,Integer>,Integer>,Integer> second = f->f.apply((a,b)->b);
        var pair = make_pair.apply(1,2);
        assertEquals(1,first.apply(pair));
        assertEquals(2,second.apply(pair));
    }
}
