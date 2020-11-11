package com.rkeeves.serialization;

import java.util.Objects;

public class BasicValueSerializer implements JsonValueSerializer {
    @Override
    public String toJson(Object instance) {
        return Objects.toString(instance,"null");
    }
}
