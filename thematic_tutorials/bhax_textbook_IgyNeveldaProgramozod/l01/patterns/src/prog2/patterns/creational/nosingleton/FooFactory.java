package prog2.patterns.creational.nosingleton;

public class FooFactory implements Factory<Foo> {

	@Override
	public Class<Foo> getProductClass() {
		return Foo.class;
	}

	@Override
	public Foo getProduct() {
		return new Foo();
	}

}
