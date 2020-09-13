package prog2.patterns.creational.builder;

public class MagicItemBuilder {
	
	
	private BaseItem baseItem;
	
	private Trait prefix;
	
	private Trait suffix;

	public BaseItem getBaseItem() {
		return baseItem;
	}

	public void setBaseItem(BaseItem baseItem) {
		this.baseItem = baseItem;
	}

	public Trait getPrefix() {
		return prefix;
	}

	public void setPrefix(Trait prefix) {
		this.prefix = prefix;
	}

	public Trait getSuffix() {
		return suffix;
	}

	public void setSuffix(Trait suffix) {
		this.suffix = suffix;
	}
	
	
	public MagicItem build() {
		return new MagicItem(baseItem, prefix, suffix);
	}
}
