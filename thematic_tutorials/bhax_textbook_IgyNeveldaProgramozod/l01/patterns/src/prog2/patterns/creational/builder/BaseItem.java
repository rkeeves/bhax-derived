package prog2.patterns.creational.builder;

public class BaseItem {
	
	public BaseItem(String name, String basic_description) {
		super();
		this.name = name;
		this.basic_description = basic_description;
	}

	public String getBasic_description() {
		return basic_description;
	}

	public String getName() {
		return name;
	}

	private final String basic_description;
	
	private final String name;

	
}
