
public class HelloConstant {

	public final static int REAL_DEAL = 10000000;
	
	private final static int PRIVATE_RYAN = 10000001;
	
	public final static int optimize_me_away = 0;
	
	public final static Thing THING = new Thing(5);
	
	public static void main(String[] args) {
		System.out.println(REAL_DEAL);
		System.out.println(optimize_me_away);
		System.out.println(PRIVATE_RYAN);
	}

}
