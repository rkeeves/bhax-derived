package com.rkeeves.refactored;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NatSetTest {

    @Test
    void testLazy() {
        Optional<Integer> expected = Optional.ofNullable(10312);
        var result = StreamSupport.stream(new NatSet().spliterator(), false)
                .filter(x -> x % 1289 == 0 && x >= 10000)
                .findFirst();
        assertEquals(expected, result);
    }
}
