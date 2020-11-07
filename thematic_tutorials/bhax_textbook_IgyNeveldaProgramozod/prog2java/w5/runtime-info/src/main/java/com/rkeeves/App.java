package com.rkeeves;

import org.reflections8.Reflections;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.scanners.TypeAnnotationsScanner;
import org.reflections8.util.ClasspathHelper;
import org.reflections8.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    final Logger logger = LoggerFactory.getLogger(App.class);

    public void run() {
        var reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.rkeeves"))
                .setScanners(new SubTypesScanner(false),
                        new TypeAnnotationsScanner()));
        logger.info("All classes");
        reflections
                .getSubTypesOf(Object.class)
                .forEach(t->logger.info("type {}",t.getTypeName()));
        logger.info("Classes annotated with FunctionalInterface");
        reflections
                .getTypesAnnotatedWith(FunctionalInterface.class)
                .forEach(t->logger.info("type {}",t.getTypeName()));
    }

    public static void main(String[] args) {
        new App().run();
    }

}
