package prog2.liskov.epm;

public class Vehicle {

	public Vehicle() {
		System.out.println("Vehicle");
	}
	
	public void start() {
		System.out.println("Vehicle.start");
	}
	
	public void start(int a) {
		System.out.println("Vehicle.start overloaded");
	}
}
