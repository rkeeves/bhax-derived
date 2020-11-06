package com.rkeeves;

import lombok.Data;

import java.math.BigInteger;

@Data
public class KeyPair {

    private final BigInteger publicKey;

    private final BigInteger m;

    private final BigInteger privateKey;
}
