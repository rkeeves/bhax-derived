package prog2.patterns.creational.builder;

public class Main {

	public static void main(String[] args) {
		maketypewriter();
		maketypedlang();
	}
	
	private static void maketypewriter() {
		MagicItemBuilder b = new MagicItemBuilder();
		b.setBaseItem(new TypeWriter());
		b.setPrefix(new FameTrait());
		b.setSuffix(new InsanityTrait());
		MagicItem mi = b.build();
		System.out.println("*****"+mi.getName()+"*****");
		System.out.println(mi.getDescription());
	}
	
	private static void maketypedlang() {
		MagicItemBuilder b = new MagicItemBuilder();
		b.setBaseItem(new ProgrammingLanguage());
		b.setPrefix(new TypedTrait());
		b.setSuffix(new UnsafeTrait());
		MagicItem mi = b.build();
		System.out.println("*****"+mi.getName()+"*****");
		System.out.println(mi.getDescription());
	}

}
