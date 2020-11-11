package com.rkeeves.serialization;

public interface JsonSerializationState {

    String toJson(Object instance, JsonSerializationContext context);
}
