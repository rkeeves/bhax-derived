package ch02s02;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShuffleExample {

	public static List<?> print(List<?> l){
		l.stream().forEach((i)->System.out.print(i+" "));
		System.out.println();
		return l;
	}
	
	public static void main(String[] args) {
		List<Integer> l = Arrays.asList(1,2,3,4,5,6,7,8,9);
		Collections.sort(l, (a,b)->a-b);
		print(l);
		Collections.shuffle(l, new Random());
		print(l);
	}

}
