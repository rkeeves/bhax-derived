package prog2.matrix;

import prog2.vec.Vec;

public class SparseMx<T> {

	
	public SparseMx(	Vec<Integer> sor, 
						Vec<Integer> oszlop, 
						Vec<T> ertek, 
						Vec<Integer> kov, 
						Vec<Integer> s, 
						Vec<Integer> o) {
		super();
		this.sor = sor;
		this.oszlop = oszlop;
		this.ertek = ertek;
		this.kov = kov;
		this.s = s;
		this.o = o;
	}
	
	
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("sor    "+sor+System.lineSeparator());
		sb.append("oszlop "+oszlop+System.lineSeparator());
		sb.append("ertek  "+ertek+System.lineSeparator());
		sb.append("kov    "+kov+System.lineSeparator());
		sb.append("s      "+s+System.lineSeparator());
		sb.append("o      "+o);
		return sb.toString();
	}


	//public static 
	private final Vec<Integer> sor;
	private final Vec<Integer> oszlop;
	private final Vec<T> ertek;
	private final Vec<Integer> kov;
	private final Vec<Integer> s;
	private final Vec<Integer> o;
	
}
