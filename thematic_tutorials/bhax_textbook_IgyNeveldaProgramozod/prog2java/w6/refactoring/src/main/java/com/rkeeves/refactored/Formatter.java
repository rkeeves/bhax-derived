package com.rkeeves.refactored;

import java.util.List;

@FunctionalInterface
public interface Formatter {

    String format(List<Integer> numbers);

}