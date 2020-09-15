import java.lang.Math;

public class PolarGen {
	private boolean store_is_empty;

	private double stored;

	public PolarGen() {
		store_is_empty = true;
	}

	public double next() {
		if (store_is_empty) {
			double u1, u2, v1, v2, w;
			do {
				u1 = Math.random();
				u2 = Math.random();
				v1 = 2 * u1 - 1;
				v2 = 2 * u2 - 1;
				w = v1 * v1 + v2 * v2;
			} while (w > 1);
			double r = Math.sqrt((-2 * Math.log(w)) / w);
			stored = r * v2;
			store_is_empty = !store_is_empty;
			return r * v1;
		} else {
			store_is_empty = !store_is_empty;
			return stored;
		}
	}

	public static void main(String[] args) {
		PolarGen g = new PolarGen();
		for (int i = 0; i < 10; ++i) {
			System.out.println(g.next());
		}
	}
}