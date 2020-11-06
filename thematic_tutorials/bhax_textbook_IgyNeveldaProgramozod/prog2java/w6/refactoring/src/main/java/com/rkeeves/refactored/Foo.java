package com.rkeeves.refactored;

public interface Foo {

    String getHello();

    default String getWorld() {
        return "world";
    }
}
