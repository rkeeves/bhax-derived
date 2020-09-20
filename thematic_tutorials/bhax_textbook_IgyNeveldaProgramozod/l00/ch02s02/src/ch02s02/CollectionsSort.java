package ch02s02;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionsSort {

	static class MyComparator1 implements Comparator<Integer>{

		@Override
		public int compare(Integer o1, Integer o2) {
			return o1-o2;
		}
		
	}
	
	static class MyComparator2 implements Comparator<Integer>{

		@Override
		public int compare(Integer o1, Integer o2) {
			return o2-o1;
		}
		
	}
	
	public static void main(String[] args) {
		List<Integer> l = Arrays.asList(1,2,3,4,5,6,7,8,9);
		Collections.sort(l, new MyComparator1());
		l.stream().forEach((i)->System.out.print(i+" "));
		System.out.println();
		Collections.sort(l, new MyComparator2());
		l.stream().forEach((i)->System.out.print(i+" "));
		System.out.println();
	}

}
