package com.rkeeves;

import org.objectweb.asm.ClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassPrinter extends ClassVisitor {

    final Logger logger = LoggerFactory.getLogger(ClassPrinter.class);

    public ClassPrinter(int api) {
        super(api);
    }

    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        logger.info("CLASS {} EXTENDS {}",name,superName);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        logger.info("NEST  {} <- {}",name,outerName);
    }
}
