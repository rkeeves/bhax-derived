package com.rkeeves;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Stack;

public class FileSystemVisit {

    final Logger logger = LoggerFactory.getLogger(FileSystemVisit.class);

    final SyntaxTreeVisitor syntaxTreeVisitor = new SyntaxTreeVisitor();

    private Stack<File> directories = new Stack<>();

    private FileSystemVisit(File rootDir) {
        directories.push(rootDir);
    }

    public static FileSystemVisit fromRootDirectory(File rootDirectory){
        if(rootDirectory.isDirectory()){
            return new FileSystemVisit(rootDirectory);
        }
        throw new RuntimeException("Supplied file for visitor was not a directory");
    }

    public void startVisit(){
       while(!directories.isEmpty()){
           this.onDirectory(directories.pop());
       }
    }

    private void onFile(File srcFile) throws FileNotFoundException {
        CompilationUnit compilationUnit = StaticJavaParser.parse(srcFile);
        syntaxTreeVisitor.setCurrentFile(srcFile);
        compilationUnit.accept(syntaxTreeVisitor,null);
    }

    private void onDirectory(File directory){
        File[] javaFiles = directory.listFiles((file,name)->name.endsWith(".java"));
        for (File javaFile : javaFiles) {
            try {
                onFile(javaFile);
            } catch (FileNotFoundException e) {
                logger.info("READ ERROR :{}", javaFile.toString());
            }
        }
        File[] subDirs = directory.listFiles(File::isDirectory);
        Arrays.stream(subDirs).forEach(directories::add);
    }
}
