package ch02s02;

public class AssertExample {
	public static void main(String[] args) {
		try {
	        assert false : "Problem";  
		}catch(AssertionError e) {
			e.printStackTrace();
		}
	}
}
