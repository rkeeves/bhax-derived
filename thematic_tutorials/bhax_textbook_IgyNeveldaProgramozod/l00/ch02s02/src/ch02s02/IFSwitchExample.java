package ch02s02;

public class IFSwitchExample {

	public static void main(String[] args) {
		if (true)
			System.out.println("A");
		else
			System.out.println("B");

		if (true) {
			System.out.println("A");
		} else {
			System.out.println("B");
		}

		int i = 0;
		switch (i) {
		case 1: {
			System.out.println("B");
			break;
		}

		default: {
			System.out.println("A");
			break;
		}
		}
		
		
	}
}
