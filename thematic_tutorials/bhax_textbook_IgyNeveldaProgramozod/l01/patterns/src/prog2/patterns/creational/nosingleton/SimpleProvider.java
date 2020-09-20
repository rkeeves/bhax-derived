package prog2.patterns.creational.nosingleton;

public class SimpleProvider<T> extends AbstractProvider<T> {

	public SimpleProvider(Factory<T> fact) {
		super(fact);
	}

	@Override
	public T get() {
		return getFactory().getProduct();
	}

}
