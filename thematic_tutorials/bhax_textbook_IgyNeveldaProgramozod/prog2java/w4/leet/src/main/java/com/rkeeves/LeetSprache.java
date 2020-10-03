package com.rkeeves;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class LeetSprache {

    static Map<Character, String[]> ciphers = new HashMap<>();
    static{
        ciphers.put('a', new String[]{"4", "4", "@", "/-\\"});
        ciphers.put('b', new String[]{"b", "8", "|3", "|}"});
        ciphers.put('c', new String[]{"c", "(", "<", "{"});
        ciphers.put('d', new String[]{"d", "|)", "|]", "|}"});
        ciphers.put('e', new String[]{"3", "3", "3", "3"});
        ciphers.put('f', new String[]{"f", "|=", "ph", "|#"});
        ciphers.put('g', new String[]{"g", "6", "[", "[+"});
        ciphers.put('h', new String[]{"h", "4", "|-|", "[-]"});
        ciphers.put('i', new String[]{"1", "1", "|", "!"});
        ciphers.put('j', new String[]{"j", "7", "_|", "_/"});
        ciphers.put('k', new String[]{"k", "|<", "1<", "|{"});
        ciphers.put('l', new String[]{"l", "1", "|", "|_"});
        ciphers.put('m', new String[]{"m", "44", "(V)", "|\\/|"});
        ciphers.put('n', new String[]{"n", "|\\|", "/\\/", "/V"});
        ciphers.put('o', new String[]{"0", "0", "()", "[]"});
        ciphers.put('p', new String[]{"p", "/o", "|D", "|o"});
        ciphers.put('q', new String[]{"q", "9", "O_", "(,)"});
        ciphers.put('r', new String[]{"r", "12", "12", "|2"});
        ciphers.put('s', new String[]{"s", "5", "$", "$"});
        ciphers.put('t', new String[]{"t", "7", "7", "'|'"});
        ciphers.put('u', new String[]{"u", "|_|", "(_)", "[_]"});
        ciphers.put('v', new String[]{"v", "\\/", "\\/", "\\/"});
        ciphers.put('w', new String[]{"w", "VV", "\\/\\/", "(/\\)"});
        ciphers.put('x', new String[]{"x", "%", ")(", ")("});
        ciphers.put('y', new String[]{"y", "", "", ""});
        ciphers.put('z', new String[]{"z", "2", "7_", ">_"});

        ciphers.put('0', new String[]{"D", "0", "D", "0"});
        ciphers.put('1', new String[]{"I", "I", "L", "L"});
        ciphers.put('2', new String[]{"Z", "Z", "Z", "e"});
        ciphers.put('3', new String[]{"E", "E", "E", "E"});
        ciphers.put('4', new String[]{"h", "h", "A", "A"});
        ciphers.put('5', new String[]{"S", "S", "S", "S"});
        ciphers.put('6', new String[]{"b", "b", "G", "G"});
        ciphers.put('7', new String[]{"T", "T", "j", "j"});
        ciphers.put('8', new String[]{"X", "X", "X", "X"});
        ciphers.put('9', new String[]{"g", "g", "j", "j"});
    }

    static Random r = new Random();

    static String fetchCipherByChar(char c) {
        String[] ss = ciphers.get(c);
        if(ss != null){
            int chance = r.nextInt(100);
            int idx;
            if(chance<91)
                idx = 0;
            else if(chance<95)
                idx = 1;
            else if(chance<98)
                idx = 2;
            else
                idx = 3;
            return ss[idx];
        }
        return "" + c;
    }

    static String applyLeet(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sb.append(fetchCipherByChar(c));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "leetspeak for the people";
        IntStream
                .range(0, 10)
                .forEach(i -> System.out.println("[" + i + "] " + applyLeet(s)));
    }
}
