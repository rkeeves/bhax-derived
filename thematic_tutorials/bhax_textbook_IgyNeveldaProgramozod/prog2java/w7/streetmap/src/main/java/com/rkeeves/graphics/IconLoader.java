package com.rkeeves.graphics;

import javax.swing.*;

public class IconLoader {

    public ImageIcon createIcon(String path, String description) {
        java.net.URL imgURL = Thread.currentThread().getContextClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.out.println("Couldn't  file: " + path);
            return null;
        }
    }
}
