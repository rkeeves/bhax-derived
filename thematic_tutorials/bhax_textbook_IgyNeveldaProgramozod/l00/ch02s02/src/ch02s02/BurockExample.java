package ch02s02;

import java.util.Collections;

public class BurockExample {
	interface Actable{
		void act();
	}
	
	static class TheActable implements Actable{

		@Override
		public void act() {
			System.out.println("act");
		}
		
	}
	
	static class TheSyncActable implements Actable{
		
		final Object mutex;
		
		Actable actable;
		
		TheSyncActable(Actable actable){
			this.actable = actable;
			mutex = this;
		}
		@Override
		public void act() {
			System.out.println("Pre Call 1");
			synchronized (mutex) {
				actable.act();
				// and more
			}
			System.out.println("Post Call 1");
		}
		
	}
	
	public static Actable wrap(Actable a) {
		return new TheSyncActable(a);
	}
	
	
	public static void main(String[] args) {
		Actable original = new TheActable(); 
		original.act();
		Actable w1 = wrap(original);
		w1.act();
	}
	
}
