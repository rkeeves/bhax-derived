package ch02s02;

public class MethodHiding {
	public static void foo() {
		System.out.println("foo");
	}
	
	static class A extends MethodHiding{
		
	}
	
	static class B extends MethodHiding{
		public static void foo() {
			System.out.println("bar");
		}
	}
	
	public static void main(String[] args) {
		MethodHiding.foo();
		A.foo();
		B.foo();
	}
}
