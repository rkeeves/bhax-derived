package prog2.patterns.creational.nosingleton;

public interface Container {
	<T, F  extends Factory<T>> Container register(Class<F> factoryClass, StorageStrategy storage);
	<T> T instance(Class<T> clazz);
}
