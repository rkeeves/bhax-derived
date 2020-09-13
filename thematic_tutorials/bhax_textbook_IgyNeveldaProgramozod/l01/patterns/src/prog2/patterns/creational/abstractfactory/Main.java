package prog2.patterns.creational.abstractfactory;

public class Main {
	/*
	 * This example program will print the same story, 
	 * but each time it takes place in a different parallel universe.
	 */
	public static void main(String[] args) {
		ParallelUniverseFactoryLocator loc = new ParallelUniverseFactoryLocator(new EntityFactory());
		generateStory(loc.getParallelUniverseFactory(ParallelUniverse.SENTIENT_PIZZAS));
		generateStory(loc.getParallelUniverseFactory(ParallelUniverse.SENTIENT_PHONES));
		generateStory(loc.getParallelUniverseFactory(ParallelUniverse.SENTIENT_FURNITURES));
	}
	
	private static String cap(String s) {
		if(s == null || s.length() < 1){
			return "";
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	private static void generateStory(ParallelUniverseFactory fact) {
		System.out.println("*****STORY*****");
		Entity character = fact.createCharacters();
		Entity sitdownable= fact.createSitdownable();
		Entity communicationDevice= fact.createCommunicationDevice();
		Entity orderable= fact.createOrderable();
		System.out.println(String.format("Two %s are sitting on %s.", character.getPlural(),sitdownable.getPlural()));
		System.out.println(String.format("One of them starts talking into a %s.", communicationDevice.getSingular()));
		System.out.println(String.format("(%s #1) I'd like to order one large %s with extra %s please!",cap(character.getSingular()),orderable.generateString(true, 0),orderable.getPlural()));
		System.out.println(String.format("(%s #2) %s!", cap(character.getSingular()), cap(orderable.generateString(false, 1))));
		System.out.println(String.format("(%s #2) No! %s, with %s on half!",cap(character.getSingular()), cap(orderable.getQualifier(2)), orderable.getQualifier(3)));
		System.out.println("***** END *****");
	}

}
