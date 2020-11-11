package com.rkeeves.serialization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonSerializer implements JsonSerializationContext {

    private static final JsonSerializationStateForObject STATE_OBJECT = new JsonSerializationStateForObject();

    private static final JsonSerializationStateForArray STATE_ARRAY = new JsonSerializationStateForArray();

    private static final JsonSerializationStateForList STATE_LIST = new JsonSerializationStateForList();

    private static final Map<Class<?>, JsonValueSerializer> VALUE_SERIALIZERS = new HashMap<>() {{
        BasicValueSerializer basicValueSerializer = new BasicValueSerializer();
        put(boolean.class, basicValueSerializer);
        put(Boolean.class, basicValueSerializer);
        put(int.class, basicValueSerializer);
        put(Integer.class, basicValueSerializer);
        put(double.class, basicValueSerializer);
        put(Double.class, basicValueSerializer);
        put(float.class, basicValueSerializer);
        put(Float.class, basicValueSerializer);
        put(long.class, basicValueSerializer);
        put(Long.class, basicValueSerializer);
        put(String.class, new StringSerializer());
    }};

    private final Map<Class<?>, JsonValueSerializer> valueSerializers;

    public JsonSerializer() {
        this.valueSerializers = VALUE_SERIALIZERS;
    }

    public String toJson(Object instance) {
        if (instance == null) {
            return "null";
        }
        var cls = instance.getClass();
        var valueSerializer = valueSerializers.get(cls);
        if (valueSerializer != null) {
            return valueSerializer.toJson(instance);
        }
        if (cls.isArray()) {
            return STATE_ARRAY.toJson(instance,this);
        } else if (instance instanceof List) {
            return STATE_LIST.toJson(instance,this);
        } else {
            return STATE_OBJECT.toJson(instance,this);
        }
    }
}
