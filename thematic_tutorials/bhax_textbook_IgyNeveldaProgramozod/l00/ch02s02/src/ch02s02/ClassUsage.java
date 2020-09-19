package ch02s02;

import java.lang.reflect.InvocationTargetException;

public class ClassUsage {

	public static void main(String[] args) {
		try {
			ClassUsage cu = (ClassUsage) ClassUsage.class.getConstructors()[0].newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			e.printStackTrace();
		}

	}

}
