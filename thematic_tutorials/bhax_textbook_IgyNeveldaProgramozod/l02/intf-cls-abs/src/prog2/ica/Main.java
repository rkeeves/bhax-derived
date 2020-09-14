package prog2.ica;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Service> l = new ArrayList<>();
		l.add(new HelloService());
		l.add(new UserBasedHelloService("Joe"));
		l.forEach(s->s.initialize());
		l.forEach(s->s.destroy());
	}

}
