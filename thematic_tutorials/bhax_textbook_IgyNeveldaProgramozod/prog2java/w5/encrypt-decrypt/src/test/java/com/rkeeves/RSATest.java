package com.rkeeves;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RSATest {

    @Test
    public void test(){
        RSA rsa = new RSA();
        var keypair = rsa.generateKeyPair(32);
        System.out.println(keypair);
        String expected = "hello world";
        BigInteger[] encrypted = rsa.encrypt(keypair.getPublicKey(),keypair.getM(),expected);
        var result = rsa.decrypt(keypair.getM(),keypair.getPrivateKey(),encrypted);
        assertEquals(expected,result);
    }
}
