package ch02s02;

public class GenericExample<A> {

	public static class Generic<A> {

		A a;

		public A getA() {
			return a;
		}

		public void setA(A a) {
			this.a = a;
		}
	}

	public static class GenericErased {

		Object a;

		public Object getA() {
			return a;
		}

		public void setA(Object a) {
			this.a = a;
		}
	}
	
	public static class GenericBounded<A extends Comparable<A>> {

		A a;

		public A getA() {
			return a;
		}

		public void setA(A a) {
			this.a = a;
		}
	}

	public static class GenericBoundedErased {

		Comparable a;

		public Comparable getA() {
			return a;
		}

		public void setA(Comparable a) {
			this.a = a;
		}
	}
	
	
	public static void main(String[] args) {
		Generic<Integer> g = new Generic<>();
		g.setA(1);
		System.out.println(g.getA());
		GenericErased ng = new GenericErased();
		ng.setA(1);
		System.out.println(  ((Integer)ng.getA())  );

		GenericBounded<Integer> g2 = new GenericBounded<>();
		g2.setA(1);
		System.out.println(g2.getA());
		GenericBoundedErased no = new GenericBoundedErased();
		no.setA(1);
		System.out.println(  ((Integer)ng.getA())  );
		
	}
}
