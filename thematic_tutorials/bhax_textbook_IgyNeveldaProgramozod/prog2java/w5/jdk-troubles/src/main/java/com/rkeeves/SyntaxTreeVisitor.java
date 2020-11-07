package com.rkeeves;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Data
public class SyntaxTreeVisitor extends VoidVisitorAdapter<Void> {

    final Logger logger = LoggerFactory.getLogger(SyntaxTreeVisitor.class);

    File currentFile;

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        super.visit(n, arg);
        String glyph = "(";
        glyph += n.isNestedType() ? "n" : ".";
        glyph += n.isInnerClass() ? "i" : ".";
        glyph += n.isStatic() ? "s" : ".";
        glyph += ")";
        logger.info("{} {} {} <- {}", glyph, n.isInterface() ? "interface" : "class", n.getName(),currentFile == null ? "null" : currentFile.getName());
    }
}
