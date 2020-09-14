package prog2.liskov.abide;

public class Square extends Rectangular {

	public Square(int sideLength) {
		this.sideLength = sideLength;
	}

	@Override
	public int getWidth() {
		return sideLength;
	}

	public void setSideLength(int v) {
		sideLength = v;
	}

	@Override
	public int getHeight() {
		return sideLength;
	}

	private int sideLength;
}
