package com.rkeeves;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class AsciiApp {

    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("You must provide the name of the source image");
            return;
        }
        String inputImageFileName = args[0];
        String textFileName = args.length < 2 ? null : args[1];

        // the exercise said that both must be written to if applicable
        List<PrintStream> printStreams = new LinkedList<>();
        printStreams.add(System.out);

        try {
            if(textFileName != null){
                printStreams.add(new PrintStream(textFileName));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File with name " + textFileName + " was not writeable.");
            return;
        }

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(inputImageFileName));
        } catch (IOException e) {
            System.out.println("Source image with name " + inputImageFileName + " was not found.");
            return;
        }

        try(Renderer renderer = new Renderer(image,printStreams) ){
            renderer.print();
        } catch (IOException e) {
            System.out.println("Renderer encountered an error.");
            e.printStackTrace();
        }
    }
}
