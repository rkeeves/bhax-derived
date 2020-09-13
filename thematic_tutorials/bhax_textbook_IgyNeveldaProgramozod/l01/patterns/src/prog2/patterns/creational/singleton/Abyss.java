package prog2.patterns.creational.singleton;

import java.util.Optional;

public class Abyss {
	private Abyss() {

	}

	private static Optional<Abyss> instance = Optional.ofNullable(null);

	public static Abyss getInstance() {
		return instance.orElseGet(() -> new Abyss());
	}

	public void gazeIntoForLong() {
		System.out.println(
				"Ve believes in nossing, Lebowski. Nossing. And tomorrow ve come back and ve cut off your chonson.");
	}
}