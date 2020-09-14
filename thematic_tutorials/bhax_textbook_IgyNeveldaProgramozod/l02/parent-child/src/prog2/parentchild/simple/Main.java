package prog2.parentchild.simple;

public class Main {

	public static void main(String[] args) {
		Sub sub = new Sub();
		Sup sup = sub;
		sub.behavior();
		sub.behaviorOther();
		sup.behavior();
		// Below line obviously generates compile time error
		// base.behaviorOther();
	}

}
