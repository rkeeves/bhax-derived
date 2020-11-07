package com.rkeeves;

public class CaesarDecryptor implements Transformer{

    private final CaesarCipher cipher;

    public CaesarDecryptor(int offset) {
        this.cipher = new CaesarCipher(offset);
    }

    @Override
    public String transform(String text) {
        return cipher.decrypt(text);
    }
}
