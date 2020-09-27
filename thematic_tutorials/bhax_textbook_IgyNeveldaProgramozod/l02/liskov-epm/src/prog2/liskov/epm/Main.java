package prog2.liskov.epm;

public class Main {

	public static void main(String[] args) {
		// SuperCar gets instantiated
		// SuperCar ctor is called
		// Car ctor is called (aka the super's ctor)
		// Vehicle ctor is called (aka the super's ctor)
		// instance gets bound to firstVehicle in method scope
		Vehicle firstVehicle = new Supercar();
		// start is not private or static,
		// because of dynamic binding the runtime type's (Supercar) start method
		// override gets called
		firstVehicle.start();
		// overloaded method
		firstVehicle.start(0);
		// the object bound to firstVehicle is (during runtime) SuperCar which is an
		// instance of Car.
		System.out.println(firstVehicle instanceof Car);
		// Unsafe cast, but with this current example it doesnt generate runtime error.
		Car secondVehicle = (Car) firstVehicle;
		// start is not private or static,
		// because of dynamic binding the runtime type's (Supercar) start method
		// override gets called
		secondVehicle.start();
		// the object bound to firstVehicle is (during runtime) SuperCar which is an
		// instance of Car.
		System.out.println(secondVehicle instanceof Supercar);
		// Compile Time Error, obviously...
		// Supercar thirdVehicle = new Vehicle();
		// thirdVehicle.start();
		
		// I added this to generate a casting related runtime error
		// for funsies!
		try {
			((Car) (Vehicle) new B()).start();
		} catch (ClassCastException e) {
			System.out.println("Runtime class cast exception");
		}
		
		firstVehicle = new Supercar();
		System.out.println("firstVehicle instanceof Supercar : " + (firstVehicle instanceof Supercar));
		firstVehicle = new Car();
		System.out.println("firstVehicle instanceof Supercar : " + (firstVehicle instanceof Supercar));
	}

}
