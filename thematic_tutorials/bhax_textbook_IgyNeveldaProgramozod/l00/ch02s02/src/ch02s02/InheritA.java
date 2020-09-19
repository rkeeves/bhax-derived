package ch02s02;

public class InheritA {
	
	int val;
	
	public void act() {
		System.out.println("A");
	}
	
	public final void act2() {
		System.out.println("A");
	}
	
	public static final class InheritASub extends InheritA{
		int val2;
		
		@Override
		public void act() {
			System.out.println("B");
		}
	}
}
