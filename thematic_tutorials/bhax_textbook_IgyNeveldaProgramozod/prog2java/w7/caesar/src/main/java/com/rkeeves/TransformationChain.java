package com.rkeeves;

import lombok.RequiredArgsConstructor;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Set;

@RequiredArgsConstructor
public class TransformationChain implements Closeable{

    private final Scanner scanner;

    private final Transformer transformer;

    private final PrintStream printStream;

    private final Set<String> exitOnTheseStrings;

    public void run() {
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(exitOnTheseStrings.contains(line)){
                return;
            }
            printStream.println(transformer.transform(line));
        }
    }

    @Override
    public void close() throws IOException {
        scanner.close();
        printStream.close();
    }
}
