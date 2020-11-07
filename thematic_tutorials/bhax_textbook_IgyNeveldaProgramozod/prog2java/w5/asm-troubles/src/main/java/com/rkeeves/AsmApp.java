package com.rkeeves;

import org.objectweb.asm.ClassReader;

import java.io.IOException;

public class AsmApp {

    public static void main(String[] args) {
        try{
            ClassReader reader = new ClassReader("com.rkeeves.demo.Foo");
            ClassPrinter classPrinter = new ClassPrinter(org.objectweb.asm.Opcodes.ASM9);
            reader.accept(classPrinter,0);
        } catch (
                IOException e) {
            System.out.println("Transformation failed");
            e.printStackTrace();
            return;
        }
    }
}
