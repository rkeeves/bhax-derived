package ch02s02;

public class GenericNesting<A> {

	Nested n = new Nested();
	
	GenericNesting(A v){
		n.v=v;
	}
	
	class Nested{
		A v;
	}
	
	static class SimpleNested{
		// A v; A is NOT useable in this class
	}
	
	
	public static void main(String[] args) {
		GenericNesting<String> gn = new GenericNesting<>("a");
		System.out.println(gn.n.v);
	}
}
