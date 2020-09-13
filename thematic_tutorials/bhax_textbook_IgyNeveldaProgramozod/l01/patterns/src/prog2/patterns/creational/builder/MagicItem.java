package prog2.patterns.creational.builder;

public class MagicItem {

	public MagicItem(BaseItem baseItem, Trait prefixTrait, Trait suffixTrait) {
		super();
		this.baseItem = baseItem;
		this.prefixTrait = prefixTrait;
		this.suffixTrait = suffixTrait;
	}

	
	
	public String getName() {
		return prefixTrait.getPrefix() + " " + baseItem.getName() + " " + suffixTrait.getSuffix();
	}
	
	public String getDescription() {
		return "This " + baseItem.getName() + " " + baseItem.getBasic_description() + ". It "+ prefixTrait.getEffect() + ", but " + suffixTrait.getEffect() + ".";
	}
	
	
	private final BaseItem baseItem;
	
	private final Trait prefixTrait;
	
	private final Trait suffixTrait;
}
