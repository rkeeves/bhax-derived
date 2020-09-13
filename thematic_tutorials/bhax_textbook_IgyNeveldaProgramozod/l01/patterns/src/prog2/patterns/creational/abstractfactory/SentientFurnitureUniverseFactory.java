package prog2.patterns.creational.abstractfactory;

public class SentientFurnitureUniverseFactory implements ParallelUniverseFactory{
	
	public SentientFurnitureUniverseFactory(EntityFactory efact) {
		this.efact = efact;
	}
	
	@Override
	public Entity createCharacters() {
		return efact.createFurniture();
	}

	@Override
	public Entity createSitdownable() {
		return efact.createPerson();
	}

	@Override
	public Entity createCommunicationDevice() {
		return efact.createPizza();
	}

	@Override
	public Entity createOrderable() {
		return efact.createPhone();
	}

	private EntityFactory efact;
}