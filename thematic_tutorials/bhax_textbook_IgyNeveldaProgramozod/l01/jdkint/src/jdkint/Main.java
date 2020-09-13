package jdkint;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Ints pool0 = new Ints();
		Ints pool1 = new Ints();
		System.out.println("This one WONT loop, because jdk caches -128");
		test(-128, -128);
		System.out.println("This one WONT loop. How? Even though we bypass jdk Integer cache, we cache it in pool0.");
		test(pool0.of(-128), pool0.of(-128));
		System.out.println("This one WILL loop, because we used different pools.");
		test(pool0.of(-128), pool1.of(-128));
		System.out.println("This one WILL loop, because it is not guaranteed to be cached below -128.");
		test(-129, -129);
		System.out
				.println("This one WONT loop, because we are explicitly caching them even though they are below -128.");
		test(pool0.of(-129), pool0.of(-129));
		System.out.println("This one WILL loop, because we use 2 different pools.");
		test(pool0.of(-129), pool1.of(-129));
	}

	public static void test(Integer x, Integer t) {
		String loop_result = (x <= t && x >= t && t != x) ? "WILL loop" : "WONT loop";
		System.out.println("x : " + x + " t : " + t + " => " + loop_result);
	}

	private static final class Ints {

		// I used map, but I could've used arrays.
		// (small chunks of a fixed sized etc.),
		// but my only intention was to get the example done.
		public final Integer of(int user_value) {
			return cache.computeIfAbsent(user_value, Integer::new);
		}

		private final Map<Integer, Integer> cache = new HashMap<>();
	}

}
