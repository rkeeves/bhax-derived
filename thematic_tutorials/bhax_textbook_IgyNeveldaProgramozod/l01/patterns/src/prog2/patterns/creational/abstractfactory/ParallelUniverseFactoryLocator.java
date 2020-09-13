package prog2.patterns.creational.abstractfactory;

public class ParallelUniverseFactoryLocator {
	
	public ParallelUniverseFactoryLocator(EntityFactory efact){
		this.efact = efact;
	}
	
	public ParallelUniverseFactory getParallelUniverseFactory(ParallelUniverse pu) {
		switch (pu) {
		case SENTIENT_APES:
			return new SentientApesUniverseFactory(efact);
		case SENTIENT_FURNITURES:
			return new SentientFurnitureUniverseFactory(efact);
		case SENTIENT_PHONES:
			return new SentientPhoneUniverseFactory(efact);
		case SENTIENT_PIZZAS:
			return new SentientPizzaUniverseFactory(efact);
		default:
			return new SentientApesUniverseFactory(efact);
		}
	}
	
	private EntityFactory efact;
}
