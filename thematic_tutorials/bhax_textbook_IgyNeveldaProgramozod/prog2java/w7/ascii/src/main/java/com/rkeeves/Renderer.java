package com.rkeeves;

import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

@RequiredArgsConstructor
public class Renderer implements Closeable {

    private final BufferedImage sourceImage;

    private final List<PrintStream> printStreams;

    public void print() throws IOException {
        for (int i = 0; i < sourceImage.getHeight(); i++) {
            for (int j = 0; j < sourceImage.getWidth(); j++) {
                char ascii = ColorToAsciiConverter.getAsciiCharByARGB(sourceImage.getRGB(j, i));
                printStreams.forEach(ps->ps.print(ascii));
            }
            printStreams.forEach(PrintStream::println);
        }
    }

    @Override
    public void close() throws IOException {
        // they dont throw
        printStreams.forEach(PrintStream::close);
    }
}