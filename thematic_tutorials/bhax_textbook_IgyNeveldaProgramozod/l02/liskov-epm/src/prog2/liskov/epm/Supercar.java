package prog2.liskov.epm;

public class Supercar extends Car{
	public Supercar() {
		System.out.println("Supercar");
	}
	
	public void start() {
		System.out.println("Supercar.start");
	}
}
