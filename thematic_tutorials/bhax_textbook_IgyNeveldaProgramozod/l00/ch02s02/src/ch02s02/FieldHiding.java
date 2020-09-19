package ch02s02;


public abstract class FieldHiding {

	FieldHiding(int a){
		this.a=a;
	}
	
	public final int a;
	
	static class A extends FieldHiding{

		A(int a) {
			super(a);
		}
		
	}
	
	static class B extends FieldHiding{
		
		B(int a, int b) {
			super(a);
			this.a = b;
		}

		public int a;
		
		public int getFunkyA() {
			return super.a;
		};
		
		public static void foo() {
			System.out.println("bar");
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new A(2).a);
		System.out.println(new B(2,3).a);
		System.out.println(new B(2,3).getFunkyA());
	}
}
