package com.rkeeves.impl;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CaesarCipher {

    private final int offset;

    public CaesarCipher(int offset) {
        if (offset < 1 || offset > 127) {
            throw new IllegalArgumentException("Offset was out of range [1,127]");
        }
        this.offset = offset;
    }

    public String decrypt(String text) {
        return doTransform(text, character -> (char) ((character - offset) % 128));
    }

    public String encrypt(String text) {
        return doTransform(text, character -> (char) ((character + offset) % 128));
    }

    private String doTransform(String text, Function<Character, Character> charTransformer) {
        return IntStream.range(0,text.length())
                .mapToObj(i->text.charAt(i))
                .map(charTransformer)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
