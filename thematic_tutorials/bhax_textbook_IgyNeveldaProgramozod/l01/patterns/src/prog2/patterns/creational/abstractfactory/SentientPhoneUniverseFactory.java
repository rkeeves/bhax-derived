package prog2.patterns.creational.abstractfactory;

public class SentientPhoneUniverseFactory implements ParallelUniverseFactory{
	
	public SentientPhoneUniverseFactory(EntityFactory efact) {
		this.efact = efact;
	}
	
	@Override
	public Entity createCharacters() {
		return efact.createPhone();
	}

	@Override
	public Entity createSitdownable() {
		return efact.createPizza();
	}

	@Override
	public Entity createCommunicationDevice() {
		return efact.createPerson();
	}

	@Override
	public Entity createOrderable() {
		return efact.createFurniture();
	}

	private EntityFactory efact;
}
