package prog2.patterns.creational.nosingleton;

public class Main {
	
	public static void main(String[] args) {
		{
			ContainerImpl ci = new ContainerImpl();
			ci.register(FooFactory.class, StorageStrategy.SINGLETON);
			Foo foo1 = ci.instance(Foo.class);
			Foo foo2 = ci.instance(Foo.class);
			System.out.println("Are instances equal? " + (foo1 == foo2));
			foo1.act();
		}
		{
			ContainerImpl ci = new ContainerImpl();
			ci.register(FooFactory.class, StorageStrategy.NEW);
			Foo foo1 = ci.instance(Foo.class);
			Foo foo2 = ci.instance(Foo.class);
			System.out.println("Are instances equal? " + (foo1 == foo2));
			foo1.act();
		}
	}

}
