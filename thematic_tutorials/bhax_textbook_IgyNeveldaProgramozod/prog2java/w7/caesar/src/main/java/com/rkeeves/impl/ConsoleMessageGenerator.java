package com.rkeeves.impl;

import com.rkeeves.api.MessageGenerator;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Scanner;

@RequiredArgsConstructor
public class ConsoleMessageGenerator implements MessageGenerator {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void start() throws IOException {

    }

    @Override
    public boolean hasNext() throws IOException {
        return true;
    }

    @Override
    public String next() throws IOException {
        return scanner.nextLine();
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
