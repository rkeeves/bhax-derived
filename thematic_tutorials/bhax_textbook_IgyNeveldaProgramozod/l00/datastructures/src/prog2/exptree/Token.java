package prog2.exptree;

public class Token {
	

	public Token(boolean numeric, String s) {
		super();
		this.numeric = numeric;
		this.s = s;
	}
	
	public final boolean numeric;
	public final String s;
}
