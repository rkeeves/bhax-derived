package prog2.patterns.creational.prototype;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		RepublicPrivate base_rp = new RepublicPrivate("Jango Fett","-");
		RepublicSpecOps base_rso = new RepublicSpecOps("Jango Fett","-");
		final String jaro = "Jaro Tapal";
		List<RepublicSoldier> soldiers = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			RepublicSoldier rp  = base_rp.clone();
			rp.reassignToJedi(jaro);
			soldiers.add(rp);
		}
		RepublicSoldier rp  = base_rso.clone();
		rp.reassignToJedi(jaro);
		soldiers.add(rp);
	
		soldiers.forEach((s)->s.executeOrder(34));
		soldiers.forEach((s)->s.executeOrder(66));
	}

}
