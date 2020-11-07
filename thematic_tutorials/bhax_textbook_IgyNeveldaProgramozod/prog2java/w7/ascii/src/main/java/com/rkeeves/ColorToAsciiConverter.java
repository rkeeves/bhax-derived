package com.rkeeves;

public class ColorToAsciiConverter {

    public static char getAsciiCharByARGB(int argb) {
        int greyscale = getGreyScale(argb);
        return getCharByGreyScale(greyscale);
    }

    private static int getGreyScale(int argb) {
        int red = (argb >> 16) & 0xff;
        int green = (argb >> 8) & 0xff;
        int blue = (argb) & 0xff;
        return (red + green + blue) / 3;
    }

    private static char getCharByGreyScale(int greyScale){
        if(greyScale <= 25){
            return 'M';
        }else if(greyScale <= 50){
            return '$';
        }else if(greyScale <= 76){
            return 'o';
        }else if(greyScale <= 102){
            return '|';
        }else if(greyScale <= 127){
            return '*';
        }else if(greyScale <= 152){
            return ':';
        }else if(greyScale <= 178){
            return '=';
        }else if(greyScale <= 204){
            return '/';
        }else if(greyScale <= 230){
            return '.';
        }else{
            return ' ';
        }
    }
}
