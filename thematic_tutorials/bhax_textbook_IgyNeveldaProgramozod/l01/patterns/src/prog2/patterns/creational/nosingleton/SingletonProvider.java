package prog2.patterns.creational.nosingleton;

public class SingletonProvider<T> extends AbstractProvider<T>{

	public SingletonProvider(Factory<T> fact) {
		super(fact);
		instance = null;
	}

	private T instance;
	
	@Override
	public T get() {
		if(instance == null)
				instance = getFactory().getProduct();
		return instance;
	}

}
