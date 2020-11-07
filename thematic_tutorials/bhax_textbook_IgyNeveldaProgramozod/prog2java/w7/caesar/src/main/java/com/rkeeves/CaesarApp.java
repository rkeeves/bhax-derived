package com.rkeeves;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CaesarApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintStream printStream = null;
        Transformer transformer = null;
        if(args.length == 2){
            try {
                transformer = new CaesarEncryptor(Integer.parseInt(args[1]));
                printStream = new PrintStream(new FileOutputStream(new File(args[0])));
            } catch (FileNotFoundException fne) {
                System.out.println("Problem during opening files");
                return;
            } catch (NumberFormatException nfe){
                System.out.println("Second argument must be a valid integer in the range of [1,127]");
                return;
            }
        }else{
            System.out.println("You must provide two arguments <outputfile> <encryptionoffset>");
            System.out.println("  outputfile       - name or path of the output file");
            System.out.println("  encryptionoffset - int in the range of [1,127]");
            return;
        }
        Set<String> consoleFinishLoopUponReadingTheseStrings = new HashSet<>();
        consoleFinishLoopUponReadingTheseStrings.add("exit");
        try(TransformationChain transformationChain = new TransformationChain(scanner,transformer,printStream,consoleFinishLoopUponReadingTheseStrings)) {
            transformationChain.run();
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println("Problem during operation");
            e.printStackTrace();
            return;
        }
    }
}
