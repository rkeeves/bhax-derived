
public class Operators {
	static boolean foo(){
		System.out.println("foo");
		return true;
	}
	
	static boolean bar(){
		System.out.println("bar");
		return true;
	}
	
	public static void main(String[] args) {
		if(foo() || bar()) {
			System.out.println("Done");
		}
		if(foo() | bar()) {
			System.out.println("Done");
		}
	}
}
