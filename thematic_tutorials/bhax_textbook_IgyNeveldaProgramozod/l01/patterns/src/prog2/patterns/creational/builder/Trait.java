package prog2.patterns.creational.builder;

public class Trait {

	public Trait(String prefix, String suffix, String effect) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
		this.effect = effect;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getEffect() {
		return effect;
	}

	public Character apply(Character c) {
		return c;
	}

	private final String prefix;

	private final String suffix;

	private final String effect;
}
