package prog2.parentchild.fin;

public class Sub extends Sup {

	public void onDestroy() {
		System.out.println("Clean up resources hogged by Sub");
	}
}
