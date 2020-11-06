package com.rkeeves;

import org.reflections8.Reflections;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.scanners.TypeAnnotationsScanner;
import org.reflections8.util.ClasspathHelper;
import org.reflections8.util.ConfigurationBuilder;

public class App {


    public static void main(String[] args) {
        var reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.rkeeves"))
                .setScanners(new SubTypesScanner(false),
                        new TypeAnnotationsScanner()));
        System.out.println("# All classes");
        reflections
                .getSubTypesOf(Object.class)
                .forEach(System.out::println);
        System.out.println("# Classes annotated with FunctionalInterface");
        reflections
                .getTypesAnnotatedWith(FunctionalInterface.class)
                .forEach(System.out::println);
    }

}
