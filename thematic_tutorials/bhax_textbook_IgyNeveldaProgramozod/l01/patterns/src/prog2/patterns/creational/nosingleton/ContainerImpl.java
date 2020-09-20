package prog2.patterns.creational.nosingleton;

import java.util.HashMap;

public class ContainerImpl implements Container {

	private HashMap<Class<?>,Provider<?>> providers = new HashMap<>();

	private <T, F extends Factory<T>> Provider<T> createProvider(Class<F> factoryClass, StorageStrategy ss){
		Factory<T> f;
		try {
			f = factoryClass.newInstance();
			switch (ss) {
			case SINGLETON:
				return new SingletonProvider<>(f);
			default:
				return new SimpleProvider<>(f);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Failed to instantiate factory of type "+factoryClass.getName()+"!");
		}
		
	}
	
	@Override
	public <T, F extends Factory<T>> Container register(Class<F> factoryClass, StorageStrategy storage) {
		Provider<T> p = createProvider(factoryClass,storage);
		providers.put(p.getProvidedClass(), p);
		return this;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public <T> T instance(Class<T> clazz) {
		Provider<?> p = providers.get(clazz);
		if(p== null) {
			throw new RuntimeException("Provider for "+clazz.getName()+" was not dound!");
		}
		return (T) p.get();
	}





	

	
	
}
