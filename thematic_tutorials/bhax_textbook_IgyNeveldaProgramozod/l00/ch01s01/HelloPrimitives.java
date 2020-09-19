
public class HelloPrimitives {

	public static void main(String[] args) {
		boolean v_bool = true;
		char v_char = 'a';
		byte v_byte = 1;
		{
			byte v_byte2 = 2;
			v_byte = 2;
		}
		short v_short = 1;
		int v_int = 1;
		long v_long = 2L;
		float v_float = 2.0f;
		double v_double = 2.0;
		char c = (char) (v_char + v_char);
	}

}
