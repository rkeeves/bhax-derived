package prog2.liskov.abide;

public abstract class Rectangular {

	public int area() {
		return getWidth() * getHeight();
	};

	public abstract int getWidth();

	public abstract int getHeight();

}
