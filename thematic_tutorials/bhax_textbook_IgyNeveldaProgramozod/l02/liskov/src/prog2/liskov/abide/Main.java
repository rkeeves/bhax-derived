package prog2.liskov.abide;

public class Main {

	public static void main(String[] args) {
		Rectangle rect = new Rectangle(5, 5);
		Square sq = new Square(5);
		Rectangular r0 = rect;
		Rectangular r1 = sq;
		System.out.println(r0.area());
		System.out.println(r1.area());
		rect.setWidth(10);
		sq.setSideLength(10);
		System.out.println(r0.area());
		System.out.println(sq.area());
	}

}
