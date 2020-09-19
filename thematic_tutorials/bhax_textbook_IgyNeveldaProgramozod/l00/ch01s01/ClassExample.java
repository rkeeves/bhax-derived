
public class ClassExample {

	public static int static_var = 0;
	
	static{
		System.out.println("STATIC INIT");
		static_var = 1;
	}
	
	public ClassExample(int field) {
		super();
		System.out.println("CTOR");
		this.field = field;
		static_var++;
	}

	int field;

	public void method() {
		System.out.println(static_var);
	}

}
