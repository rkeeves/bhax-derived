package com.rkeeves;

public class HelloJNI {  // Save as HelloJNI.java
    static {
        System.loadLibrary("hello");
    }

    private native void sayHello();

    public static void main(String[] args) {
        new HelloJNI().sayHello();
    }
}
