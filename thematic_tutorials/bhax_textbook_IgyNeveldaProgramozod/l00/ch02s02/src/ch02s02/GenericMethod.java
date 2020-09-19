package ch02s02;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.Vector;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class GenericMethod {

	public static <T> T identity(T v) {
		return v;
	}

	public static <T extends Comparable<T>> int cmp(T a, T b) {
		return a.compareTo(b);
	}

	public static <T extends List<? extends Number>> double sum(T l) {
		double acc = 0.0;
		for (Number n : l)
			acc += n.doubleValue();
		return acc;
	}
	
	public static <E, T extends List<E>> E electricboogaloo(T l, E identity, BinaryOperator<E> add) {
		return l.stream().reduce(identity, add);
	}

	public static void main(String[] args) {
		System.out.println(identity("good"));
		System.out.println(identity(cmp(1, 2)));
		System.out.println(sum(Arrays.asList(1, 2, 3, 4, 5)));
		System.out.println(electricboogaloo(Arrays.asList(1, 2, 3, 4, 5),0,(r,n)->r+n));
	}

}
