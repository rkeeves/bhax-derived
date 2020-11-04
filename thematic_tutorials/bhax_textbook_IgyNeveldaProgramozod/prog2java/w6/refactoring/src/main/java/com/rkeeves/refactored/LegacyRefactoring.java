package com.rkeeves.refactored;

import com.rkeeves.old.Calculator;
import com.rkeeves.old.Formatter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Adott egy “legacy” kód mely tartalmaz anonymus interface implementációkat, ciklusokat és feltételes kifejezések.
 * Ebben a feladatban ezt a “legacy” kódot szeretnénk átírni lambda kifejezések segítségével (metódus referencia használata előnyt jelent).
 * <p>
 * A program jelenlegi kimenete:
 * Runnable!
 * Calculation result: 9
 * Result numbers:
 * 1
 * 9
 * 25
 * Formatted numbers: 1925
 */
public class LegacyRefactoring {

    public static void main(String[] args) {
        new LegacyRefactoring().legacy();
    }

    public void legacy() {
        Runnable runnable = () -> System.out.println("Runnable!");
        runnable.run();

        Calculator calculator = (x) -> x * x;

        Integer result = calculator.calculate(3);
        System.out.println("Calculation result: " + result);

        List<Integer> inputNumbers = Arrays.asList(1, null, 3, null, 5);
        List<Integer> resultNumbers = inputNumbers
                .stream()
                .filter(Objects::nonNull)
                .map(calculator::calculate)
                .collect(Collectors.toList());

        System.out.println("Result numbers: ");
        resultNumbers.forEach(System.out::println);

        Formatter formatter = (ints) -> ints
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
        System.out.println("Formatted numbers: " + formatter.format(resultNumbers));
    }

}
