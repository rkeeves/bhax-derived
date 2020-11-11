package com.rkeeves.serialization;

import java.lang.reflect.Field;
import java.util.StringJoiner;

public class JsonSerializationStateForObject implements JsonSerializationState {
    @Override
    public String toJson(Object instance, JsonSerializationContext context) {
        var cls = instance.getClass();
        StringJoiner sj = new StringJoiner(",", "{", "}");
        for (Field field : cls.getDeclaredFields()) {
            if (!field.canAccess(instance)) {
                try {
                    field.setAccessible(true);
                } catch (Exception e) {
                    throw new RuntimeException("Error during trying brute forcing access to field instead of looking up getters for field " + field.getName());
                }
            }
            String value;
            try {
                value = context.toJson(field.get(instance));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error during accessing field by brute force instead of looking up getters for field " + field.getName());
            }
            sj.add("\"" + field.getName() + "\"" + ":" + value);
        }
        return sj.toString();
    }
}
