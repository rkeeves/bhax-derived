package intf;

public interface PostJ8CompliantInterface {

	default void feature3() {
		feature2.equals(feature2);
	}
	
	static PlansToMakeJdkFreeAgain feature1() {
		return feature2;
	}
	
	// null intentionally
	static PlansToMakeJdkFreeAgain feature2 = null;
}
