package prog2.objovrride;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		foobarItLikeTheresNoTomorrow(NotOverridedObject.class);
		foobarItLikeTheresNoTomorrow(EqOverriddenObject.class);
		foobarItLikeTheresNoTomorrow(HashOverriddenObject.class);
		foobarItLikeTheresNoTomorrow(OverriddenObject.class);
	}

	// ...ugly but gets the job done, especially that this is not production code. :)
	@SuppressWarnings("unchecked")
	public static <T> void foobarItLikeTheresNoTomorrow(Class<T> cls) {
		{
			String[] features = new String[] { "OOP", "GC" };
			System.out.println("***** " + cls.getName() + " *****");
			try {
				Constructor<T> ctor = (Constructor<T>) cls.getConstructors()[0];
				T a = ctor.newInstance("Java", features);
				T b = ctor.newInstance("Java", features);
				System.out.println("a : " + a);
				System.out.println("b : " + b);
				System.out.println("Equal? " + a.equals(b));
				System.out.println("Hash?  " + (a.hashCode() == b.hashCode()));
				System.out.println("Set:");
				Set<T> s = new HashSet<>();
				s.add(a);
				s.add(b);
				s.forEach((o)->System.out.println("  "+o));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}

		}
	}

}
