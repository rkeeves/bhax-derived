package prog2.liskov.basic;

public class Variance {

	static class A {

	}

	static class B extends A{
		B foo(B o) {
			return null;
		}
	}

	static class C extends B{
		/* This results in an error,
		 * because although this is allowed according to Liskov,
		 * in Java technically it is an overload.
		@Override
		B foo(A o) {
			return null;
		}
		*/
	}
	
	static class D extends B{
		@Override
		D foo(B o) {
			return null;
		}
		
	}
	
	public static void main(String[] args) {
	
	}

}
