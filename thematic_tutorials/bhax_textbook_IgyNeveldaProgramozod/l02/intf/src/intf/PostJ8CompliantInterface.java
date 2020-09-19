package intf;

public interface PostJ8CompliantInterface {

	default void act() {
		System.out.println("act");
	}
	
	static void act_static() {
		System.out.println("act_static");
	}

}
