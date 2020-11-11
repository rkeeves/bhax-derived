package com.rkeeves.serialization;

import java.lang.reflect.Array;
import java.util.StringJoiner;

public class JsonSerializationStateForArray implements JsonSerializationState {

    @Override
    public String toJson(Object instance, JsonSerializationContext context) {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        var length = Array.getLength(instance);
        for (int i = 0; i < length; i++) {
            sj.add(context.toJson(Array.get(instance, i)));
        }
        return sj.toString();
    }
}
