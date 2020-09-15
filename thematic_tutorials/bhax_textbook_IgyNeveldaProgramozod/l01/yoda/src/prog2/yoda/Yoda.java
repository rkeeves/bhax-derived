package prog2.yoda;

import java.util.Optional;

public class Yoda {
	
	private final static String YouMust = "a";
	
	public static void foo() {
		System.out.println("bar");
	}
	
	public static void main(String[] args) {
		String something = null;
		try {
			unsafe_compare(something);
		} catch (Exception e) {
			System.out.println(something.format("Go %s, go!", "Yoda"));
		}
		System.out.println("safe_compare: " + safe_compare(something));
		System.out.println("safe_compare_opt: " + safe_compare_opt(Optional.ofNullable(something)));
	}

	
	private static boolean unsafe_compare(String toThisString) {
		return (toThisString.equals(YouMust));
	}
	
	private static boolean safe_compare(String userString) {
		return (YouMust.equalsIgnoreCase(userString));
	}
	
	private static Boolean safe_compare_opt(Optional<String> userString) {
		return (userString.map((s)->s.equalsIgnoreCase(YouMust))).orElse(false);
	}
}
