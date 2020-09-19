package ch02s02;

public interface InterfaceExample {
	void act();

	public interface InterfaceExampleSub<T> extends InterfaceExample{
		T act2();
	}
	public class H implements InterfaceExampleSub<H> {

		@Override
		public void act() {
			System.out.println("act");
		}


		@Override
		public H act2() {
			System.out.println("act2");
			return this;
		}
		

		public static void main(String[] args) {
			new H().act2().act();
			
			InterfaceExample intf = new H();
			intf.act();
		}
	}
}
