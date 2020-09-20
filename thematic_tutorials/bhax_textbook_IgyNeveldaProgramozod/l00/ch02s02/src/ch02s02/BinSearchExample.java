package ch02s02;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BinSearchExample {

	public static void main(String[] args) {
		List<Integer> current = Arrays.asList(1,2,3,4,5,6,7,8,9);
		int idx = Collections.binarySearch(current, 6, (a,b)->a-b);
		if(idx < 0) {
			System.out.println(" Not found");
			return;
		}
		System.out.println("Found at index : " + idx + " value (sanity check): " + current.get(idx));
		System.out.println("Max " +Collections.max(current));
		System.out.println("Min " +Collections.min(current));
		
	}

}
