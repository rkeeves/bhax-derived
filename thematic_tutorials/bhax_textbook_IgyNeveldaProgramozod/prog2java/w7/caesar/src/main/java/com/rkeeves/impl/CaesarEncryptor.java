package com.rkeeves.impl;

import com.rkeeves.api.MessageHandler;

import java.io.IOException;

public class CaesarEncryptor implements MessageHandler {

    private final CaesarCipher cipher;

    public CaesarEncryptor(int offset) {
        this.cipher = new CaesarCipher(offset);
    }

    @Override
    public void start() throws IOException {

    }

    @Override
    public boolean shouldTerminate(String message) {
        return false;
    }

    @Override
    public String handle(String text) {
        return cipher.encrypt(text);
    }


    @Override
    public void close() {

    }
}
