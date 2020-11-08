package com.rkeeves.impl;

import com.rkeeves.api.MessageHandler;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@RequiredArgsConstructor
public class FilePrinter implements MessageHandler {

    private final String file;

    private PrintStream printStream;

    @Override
    public void start() throws IOException {
        printStream = new PrintStream(new FileOutputStream(new File(file)));
    }

    @Override
    public boolean shouldTerminate(String message) {
        return false;
    }

    @Override
    public String handle(String message) throws IOException {
        printStream.println(message);
        return message;
    }

    @Override
    public void close() throws IOException {
        printStream.close();
        System.out.println("Closed output file " + file);
    }
}
