package com.rkeeves;

import java.io.File;

public class JdkTroubles {

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Please provide the path for the jdk sources root folder");
            return;
        }
        String jdkRootFolder = args[0];
        FileSystemVisit fileSystemVisit = FileSystemVisit.fromRootDirectory(new File(jdkRootFolder));
        fileSystemVisit.startVisit();
    }
}
