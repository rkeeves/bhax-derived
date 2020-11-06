package com.rkeeves;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

public class FooStuffProducer implements Closeable{

    private static class FakeWriter extends Writer {

        private StringBuffer sb = new StringBuffer();
        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            int sz = off+len;
            for (int i = off; i < sz; i++) {
                sb.append(cbuf[i]);
            }
        }

        @Override
        public void flush() throws IOException {
            System.out.println(sb.toString());
            sb = new StringBuffer();
        }

        @Override
        public void close() throws IOException {
            flush();
        }
    }

    private final Writer writer;

    public FooStuffProducer() throws IOException {
        writer = new FakeWriter();
    }

    public void writeStuff() throws IOException {
        writer.write("Stuff");
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

    public static void main(String[] args) {
        try(FooStuffProducer bsp = new FooStuffProducer()){
            bsp.writeStuff();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
