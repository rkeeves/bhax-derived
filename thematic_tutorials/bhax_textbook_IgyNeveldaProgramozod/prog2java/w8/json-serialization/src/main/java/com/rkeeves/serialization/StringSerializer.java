package com.rkeeves.serialization;

public class StringSerializer implements JsonValueSerializer {
    @Override
    public String toJson(Object instance) {
        return instance == null ? "\"\"" : "\""+instance+"\"";
    }
}
