package com.rkeeves.serialization;

interface JsonValueSerializer {

    String toJson(Object instance);
}