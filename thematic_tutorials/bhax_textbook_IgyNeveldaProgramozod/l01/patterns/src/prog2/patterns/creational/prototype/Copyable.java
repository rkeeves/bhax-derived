package prog2.patterns.creational.prototype;

public interface Copyable<T> extends Cloneable{
	public T copy();
}
