package com.rkeeves.serialization;

import java.util.List;
import java.util.StringJoiner;

public class JsonSerializationStateForList implements JsonSerializationState {
    @Override
    public String toJson(Object instance, JsonSerializationContext context) {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        List<?> list = (List) instance;
        for (Object o : list) {
            sj.add(context.toJson(o));
        }
        return sj.toString();
    }
}
