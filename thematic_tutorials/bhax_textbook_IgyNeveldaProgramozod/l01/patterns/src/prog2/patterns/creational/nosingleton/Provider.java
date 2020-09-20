package prog2.patterns.creational.nosingleton;

public interface Provider<T> {
	Class<T> getProvidedClass();
	T get();
}
