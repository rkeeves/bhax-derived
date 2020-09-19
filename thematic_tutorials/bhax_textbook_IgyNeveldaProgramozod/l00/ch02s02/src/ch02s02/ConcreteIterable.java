package ch02s02;

import java.util.Iterator;

public class ConcreteIterable implements Iterable<Integer>, Iterator<Integer> {

	private int cur = 0;
	
	@Override
	public Iterator<Integer> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return cur != 10;
	}

	@Override
	public Integer next() {
		return cur++;
	}
	
	public static void main(String[] args) {
		ConcreteIterable ci = new ConcreteIterable();
		for (Integer i : ci) {
			System.out.println(i);
		}
	}

}
