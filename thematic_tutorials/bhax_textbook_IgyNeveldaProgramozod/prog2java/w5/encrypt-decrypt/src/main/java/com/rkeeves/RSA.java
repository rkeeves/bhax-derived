package com.rkeeves;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;

public class RSA {

    public KeyPair generateKeyPair(int bitLength){
        BigInteger p = new BigInteger(bitLength, 100, new Random());
        BigInteger q = new BigInteger(bitLength, 100, new Random());
        BigInteger m = p.multiply(q);
        BigInteger z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger d;
        do{
            do{
                d = new BigInteger(bitLength, new Random());

            }while(d.equals(BigInteger.ONE));
        }while(!z.gcd(d).equals(BigInteger.ONE));
        BigInteger e = d.modInverse(z);
        return new KeyPair(e,m,d);
    }

    public BigInteger[] encrypt(BigInteger publicKey, BigInteger messageCode, String toBeEncrypted) {
        return encrypt(publicKey, messageCode, toBeEncrypted.getBytes());
    }

    public BigInteger[] encrypt(BigInteger publicKey, BigInteger messageCode, byte[] toBeEncrypted) {
        return IntStream.range(0,toBeEncrypted.length)
                .mapToObj(i -> new BigInteger(new byte[]{toBeEncrypted[i]})
                        .modPow(publicKey, messageCode)).toArray(BigInteger[]::new);
    }

    public String decrypt(BigInteger messageCode, BigInteger privateKey, BigInteger[] encrypted) {
        byte[] result = new byte[encrypted.length];
        IntStream.range(0,encrypted.length)
                .forEach(i->result[i]=encrypted[i].modPow(privateKey,messageCode).byteValue());
        return new String(result);
    }

}