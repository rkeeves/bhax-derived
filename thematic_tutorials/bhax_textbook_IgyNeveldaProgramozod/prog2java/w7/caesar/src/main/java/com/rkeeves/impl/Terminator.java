package com.rkeeves.impl;

import com.rkeeves.api.MessageHandler;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class Terminator implements MessageHandler {

    private final String terminatorText;

    @Override
    public void start() throws IOException {

    }

    @Override
    public boolean shouldTerminate(String message) {
        return terminatorText.equals(message);
    }

    @Override
    public String handle(String message) throws IOException {
        return message;
    }

    @Override
    public void close() throws IOException {

    }
}
