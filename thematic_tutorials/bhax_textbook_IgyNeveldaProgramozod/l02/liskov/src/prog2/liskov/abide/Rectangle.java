package prog2.liskov.abide;

public class Rectangle extends Rectangular {

	public Rectangle(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	public void setWidth(int v) {
		width = v;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public void setHeight(int v) {
		height = v;
	}

	private int height;

	private int width;
}
