package prog2.patterns.creational.abstractfactory;

public class SentientApesUniverseFactory implements ParallelUniverseFactory{
	
	public SentientApesUniverseFactory(EntityFactory efact) {
		this.efact = efact;
	}
	
	@Override
	public Entity createCharacters() {
		return efact.createPerson();
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
		return efact.createPizza();
	}

	private EntityFactory efact;
}