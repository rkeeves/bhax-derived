package intf;

public class Racle implements PostJ8CompliantInterface {

	public static void main(String[] args) {
		new PostJ8CompliantInterface() {
		}.act();
		
		new PreJ8CompliantInterface() {

			@Override
			public void act() {
				System.out.println("act casually");
			}
		}.act();
	}
}
