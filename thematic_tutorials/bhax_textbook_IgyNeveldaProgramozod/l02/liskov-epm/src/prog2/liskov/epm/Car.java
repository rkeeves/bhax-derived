package prog2.liskov.epm;

public class Car extends Vehicle{
	public Car() {
		System.out.println("Car");
	}
	
	public void start() {
		System.out.println("Car.start");
	}
}
