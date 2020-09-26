package prog2.parentchild.simple;

public class Main {

	static class Sup {

		public void behavior() {
			System.out.println("Sup->behavior");
		}

		public void behavior2(Number i) {
			System.out.println("Sup->behavior2");
		}

		public void behavior2(Integer i) {
			System.out.println("Sup->behavior2 Integer overload");
		}
	}

	static class Sub extends Sup {

		public void behavior() {
			System.out.println("Sub->behavior");
		}

		public void behavior2(Number i) {
			System.out.println("Sub->behavior2");
		}
		
		public void behaviorOther() {
			System.out.println("Sub->behaviorOther");
		}
	}
	
	static class Sub2 extends Sup {

		public void behavior() {
			System.out.println("Sub2->behavior2");
		}

		public void behavior2(Number i) {
			System.out.println("Sub2->behavior2");
		}
		
		public void behavior2(Integer i) {
			System.out.println("Sub2->behavior2 Integer overload");
		}
		
		public void behaviorOther() {
			System.out.println("Sub2->behaviorOther");
		}
	}

	public static void main(String[] args) {
		System.out.println("# Static Sub -- Dynamic Sub");
		Sub a = new Sub();
		a.behavior();
		a.behaviorOther();
		System.out.println("# Static Sup -- Dynamic Sub");
		Sup b = a;
		b.behavior();
		// Below line obviously generates compile time error
		// b.behaviorOther();
		b.behavior2(Double.valueOf(0));
		b.behavior2(Integer.valueOf(0));
		System.out.println("# Static Sup -- Dynamic Sub2");
		b = new Sub2();
		b.behavior();
		b.behavior2(Double.valueOf(0));
		b.behavior2(Integer.valueOf(0));
	}

}
