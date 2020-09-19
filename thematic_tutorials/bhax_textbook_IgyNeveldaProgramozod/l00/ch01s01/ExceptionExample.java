import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionExample {

	public static final class SummonBearException extends Exception {

	}

	public static final class SpanishInquisition extends RuntimeException {

		public SpanishInquisition() {
			super("You never expect the spanish inquisition!");
		}

	}

	public static void your_puny_catch_has_no_power_over_me() throws SummonBearException {
		throw new SpanishInquisition();
	}

	public static void nothrow() throws Exception {
		return;
	}

	public static void main(String[] args) {
		try {
			your_puny_catch_has_no_power_over_me();
		} catch (SummonBearException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}finally {
			System.out.println("I always prevail");
		}
		try {
			nothrow();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("I also always prevail");
		}		
	}
}
