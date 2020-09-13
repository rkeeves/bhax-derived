package prog2.patterns.creational.abstractfactory;

public class SentientPizzaUniverseFactory implements ParallelUniverseFactory{

	public SentientPizzaUniverseFactory(EntityFactory efact) {
		this.efact = efact;
	}
	
	@Override
	public Entity createCharacters() {
		return efact.createPizza();
	}

	@Override
	public Entity createSitdownable() {
		return efact.createFurniture();
	}

	@Override
	public Entity createCommunicationDevice() {
		return efact.createPhone();
	}

	@Override
	public Entity createOrderable() {
		return efact.createPerson();
	}

	private EntityFactory efact;
}
