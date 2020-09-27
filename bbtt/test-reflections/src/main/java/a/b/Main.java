package a.b;

import java.util.Set;

import org.reflections.Reflections;

import a.c.Foo;

public class Main {

	public static void main(String[] args) {
		Reflections reflections = new Reflections("a");

		Set<Class<? extends Foo>> subTypes = reflections.getSubTypesOf(Foo.class);
		subTypes.forEach(System.out::println);
		
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Tagged.class);
		annotated.forEach(System.out::println);

	}

}
