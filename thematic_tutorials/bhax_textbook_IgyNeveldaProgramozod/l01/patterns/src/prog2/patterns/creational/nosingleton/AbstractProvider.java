package prog2.patterns.creational.nosingleton;

public abstract class AbstractProvider<T> implements Provider<T> {
	
	public AbstractProvider(Factory<T> fact) {
		super();
		this.fact = fact;
	}
	
	protected Factory<T> getFactory() {
		return fact;
	}
	
	private final Factory<T> fact;

	@Override
	public Class<T> getProvidedClass() {
		return fact.getProductClass();
	}
}
