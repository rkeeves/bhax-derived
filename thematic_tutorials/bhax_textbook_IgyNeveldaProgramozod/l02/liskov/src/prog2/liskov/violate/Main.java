package prog2.liskov.violate;

public class Main {

	public static void main(String[] args) {
		Rectangle r = new Square(10);
		System.out.println(r.area());
		r.setHeight(100);
		System.out.println(r.area());
	}

}
