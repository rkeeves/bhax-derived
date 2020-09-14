package prog2.liskov.violate;

public class Rectangle {

	public Rectangle(int height, int width) {
		super();
		this.height = height;
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int area() {
		return height * width;
	}

	private int height;

	private int width;
}
