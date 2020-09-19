
public class Outer {
	
	private int v = 5;
	
	static class Inner{
		
		int getv() {
			return 6;
		}
	}
	
	public static void main(String[] args) {
		Outer o = new Outer();
		Inner i = new Inner();
		System.out.println(i.getv());
	}
}
