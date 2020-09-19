
public enum EnumExample implements Valuatable{
	ZERO(null),
	ONE(ZERO),
	TWO(ONE) {
		@Override
		public void act() {
			System.out.println("Click That Bait!");
		}
	};
	
	
	EnumExample(EnumExample parent) {
		this.parent = parent;
	}
	
	@Override
	public int value() {
		return(parent == null) ? 0 :  parent.value() + 1;
	}
	
	public void act() {
		System.out.println("Oh boy why did you called this?");
	}
	
	private final EnumExample parent;

	public static void main(String[] args) {
		EnumExample[]  a = EnumExample.values();
		for (EnumExample e : a) {
			System.out.println(e.name() + ", value is " + e.value());
			e.act();
		}
	}
}
