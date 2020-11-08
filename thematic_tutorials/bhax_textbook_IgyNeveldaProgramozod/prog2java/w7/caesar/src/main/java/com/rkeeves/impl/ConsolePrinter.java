package com.rkeeves.impl;

import com.rkeeves.api.MessageHandler;

import java.io.IOException;

public class ConsolePrinter implements MessageHandler {
    @Override
    public void start() throws IOException {

    }

    @Override
    public boolean shouldTerminate(String message) {
        return false;
    }

    @Override
    public String handle(String message) throws IOException {
        System.out.println(message);
        return message;
    }

    @Override
    public void close() throws IOException {

    }
}
