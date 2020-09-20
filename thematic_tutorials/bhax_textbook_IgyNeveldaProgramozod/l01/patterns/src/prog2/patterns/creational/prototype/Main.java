package prog2.patterns.creational.prototype;

public class Main {

	private Entity theEntity = new Entity();
	
	private Hurray theHurray = new Hurray();
	
	public static void main(String[] args) {
		Main m = new Main();
		client_code(m);
	}
	
	public static void client_code(Main main) {
		Entity e0 = main.theEntity.copy();
		e0.add("fname", "Wendy");
		e0.add("lname", "Testaburger");
		
		Entity e1 = main.theEntity.copy();
		e1.add("fname", "Stan");
		e1.add("lname", "Marsh");
		
		Entity e2 = e1.copy();
		e2.add("fname", "Randy");
		
		Hurray h0 = main.theHurray.copy();
		h0.add(1, "a");
		h0.add(2, "b");
		
		Hurray h1 = main.theHurray.copy();
		h1.add(1, "c");
		h1.add(2, "d");
		
		System.out.println(e0);
		System.out.println(e1);
		System.out.println(e2);
		System.out.println(h0);
		System.out.println(h1);
	}

}
