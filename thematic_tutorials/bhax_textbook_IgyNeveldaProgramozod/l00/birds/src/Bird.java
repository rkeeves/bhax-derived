
public class Bird {

	public void fly() {

	};

	static class Eagle extends Bird {

		@Override
		public void fly() {
			System.out.println("Wakes up, looks at the sky boldly then lifts off into the air.");
		}

	}

	static class DeadBird extends Bird {

		@Override
		public void fly() {
			System.out.println("Doesnt move at all, but smells really awfully.");
		}

	}

	static class Penguin extends Bird {

		@Override
		public void fly() {
			System.out.println("NO WAY!");
		}

	}

	public static void main(String[] args) {
		Bird birds[] = new Bird[] { new Eagle(), new DeadBird(), new Penguin() };
		for (Bird bird : birds) {
			bird.fly();
		}
	}
}
