package com.rkeeves.api;

import java.io.IOException;

public interface MessageHandler {

    void start() throws IOException;

    boolean shouldTerminate(String message);

    String handle(String message) throws IOException;

    void close() throws IOException;
}
