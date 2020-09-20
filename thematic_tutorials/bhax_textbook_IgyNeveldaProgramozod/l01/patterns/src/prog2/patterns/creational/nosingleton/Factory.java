package prog2.patterns.creational.nosingleton;

public interface Factory<T> {
	Class<T> getProductClass();
	T getProduct();
}
