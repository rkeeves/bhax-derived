package com.rkeeves;

public class CaesarEncryptor implements Transformer{

    private final CaesarCipher cipher;

    public CaesarEncryptor(int offset) {
        this.cipher = new CaesarCipher(offset);
    }

    @Override
    public String transform(String text) {
        return cipher.encrypt(text);
    }
}
