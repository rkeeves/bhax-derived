package com.rkeeves.api;

import lombok.RequiredArgsConstructor;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class Chain implements Closeable {

    private final MessageGenerator messageGenerator;

    private final List<MessageHandler> handlers;

    public void run() throws IOException {
        start();
        boolean isRunning = true;
        while(messageGenerator.hasNext() && isRunning){
            String message = messageGenerator.next();
            for (MessageHandler handler : handlers) {
                if(handler.shouldTerminate(message)){
                    isRunning = false;
                    break;
                }
                message = handler.handle(message);
            }
        }
    }

    private void start() throws IOException {
        messageGenerator.start();
        for (MessageHandler handler : handlers) {
            handler.start();
        }
    }

    public void close() throws IOException {
        IOException exception = null;
        try {
            messageGenerator.close();
        } catch (IOException e) {
            exception = e;
        }
        for (MessageHandler handler : handlers) {
            try {
                handler.close();
            } catch (IOException e) {
                exception = e;
            }
        }
        if(exception!=null)
            throw exception;
    }
}
