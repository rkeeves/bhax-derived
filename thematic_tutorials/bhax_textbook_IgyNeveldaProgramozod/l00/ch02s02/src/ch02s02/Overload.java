package ch02s02;

public class Overload {

	public static int add(char a, char b) {
		return a + b;
	}
	
	public static int add(int...a) {
		int acc = 0;
		for (int i : a) {
			acc += i;
		}
		return acc;
	}
	
	public static void main(String[] args) {
		System.out.println(add('1','a'));
		System.out.println(add(1,2,3));
	}
}
