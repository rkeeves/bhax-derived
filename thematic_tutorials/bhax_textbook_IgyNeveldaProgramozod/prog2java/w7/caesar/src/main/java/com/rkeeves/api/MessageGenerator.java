package com.rkeeves.api;

import java.io.IOException;

public interface MessageGenerator {

    void start() throws IOException;

    boolean hasNext() throws IOException;

    String next() throws IOException;

    void close() throws IOException;
}
