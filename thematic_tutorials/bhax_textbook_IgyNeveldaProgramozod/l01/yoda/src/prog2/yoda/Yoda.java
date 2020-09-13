package prog2.yoda;

import java.util.Optional;

public class Yoda {
	
	private final static String EXPECTED = "a";
	
	public static void main(String[] args) {
		String user_name = null;
		try {
			System.out.println("unsafe_compare: " + unsafe_compare(user_name));
		} catch (Exception e) {
			System.out.println("unsafe_compare FAILED!");
		}
		System.out.println("safe_compare: " + safe_compare(user_name));
		System.out.println("safe_compare_opt: " + safe_compare_opt(Optional.ofNullable(user_name)));
	}

	
	private static boolean unsafe_compare(String userString) {
		return (userString.equalsIgnoreCase(EXPECTED));
	}
	
	private static boolean safe_compare(String userString) {
		return (EXPECTED.equalsIgnoreCase(userString));
	}
	
	private static Boolean safe_compare_opt(Optional<String> userString) {
		return (userString.map((s)->s.equalsIgnoreCase(EXPECTED))).orElse(false);
	}
}
