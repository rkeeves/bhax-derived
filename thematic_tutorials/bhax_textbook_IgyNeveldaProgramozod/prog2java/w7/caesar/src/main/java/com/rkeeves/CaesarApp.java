package com.rkeeves;

import com.rkeeves.api.Chain;
import com.rkeeves.api.ChainBuilder;
import com.rkeeves.impl.Terminator;
import com.rkeeves.impl.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CaesarApp {

    public static void main(String[] args) {
        if(args.length != 2){
            System.out.println("You must provide two arguments <outputfile> <encryptionoffset>");
            System.out.println("  outputfile       - name or path of the output file");
            System.out.println("  encryptionoffset - int in the range of [1,127]");
            return;
        }
        String outputFileName = args[0];
        int encryptionOffset;
        try {
            encryptionOffset = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            System.out.println("Second argument must be a valid int in the range of [1,127]");
            return;
        }
        try(Chain chain = ChainBuilder
                .builder()
                .messageGenerator(new ConsoleMessageGenerator())
                .chain(new Terminator("exit"))
                .chain(new CaesarEncryptor(encryptionOffset))
                .chain(new ConsolePrinter())
                .chain(new FilePrinter(outputFileName))
                .build()
                .get())
        {
            chain.run();
            System.out.println("Done");
        } catch (FileNotFoundException fne) {
            System.out.println("Problem during opening files");
            fne.printStackTrace();
        } catch (IOException e) {
            System.out.println("Problem during operation");
            e.printStackTrace();
        }
    }
}
