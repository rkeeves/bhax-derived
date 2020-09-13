package bbp;

public class BBP {

	private static final double PRECISION_MULT = 0.0;
	
	private static final char[] chars = new char[] {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	private final int d;
	
	private String hexadecPi;
	
	public BBP(int d) {
		this.d=d;
		this.hexadecPi = "";
	}
	
	public String getHexadecPi() {
		return hexadecPi;
	}
	
	public void calculate() {
		if (d <= 0)
			return;
		double d0xPi = 0.0;

		double d0xS1 = d0xS(1);
		double d0xS4 = d0xS(4);
		double d0xS5 = d0xS(5);
		double d0xS6 = d0xS(6);
		
		d0xPi = 4.0 * d0xS1 - 2.0 * d0xS4 - d0xS5 - d0xS6;
		d0xPi -= Math.floor(d0xPi);
		StringBuilder sb = new StringBuilder();
		while(d0xPi != 0.0) {
			int hexVal = (int) Math.floor(16.0*d0xPi);
			sb.append(chars[hexVal]);
			d0xPi = 16.0*d0xPi - Math.floor(16.0*d0xPi);
		}
		this.hexadecPi = sb.toString();
	}

	public double d0xS(int j) {
		double accu = 0.0;
		for (int k = 0; k <= d; k++) {
			accu += (double)calc_16_n_mod_k(d-k,8*k+j) / (double) (8*k+j);
		}
		for (int k = d+1; k <= d*PRECISION_MULT; k++) {
			accu += Math.pow(16, d-k) / (double) (8*k+j);
		}
		return accu;
	}

	public long calc_16_n_mod_k(int n, int k) {
		int t = (n <= 0) ? 0 : Integer.highestOneBit(n);
		long r = 1;
		while (true) {
			if (n >= t) {
				r = (16 * r) % k;
				n -= t;
			}
			t /= 2;
			if (t >= 1) {
				r = (r * r) % k;
			}else {
				break;
			}
		}
		return r;
	}

	public static void main(String[] args) {
		BBP bbp = new BBP(1000000);
		bbp.calculate();
		System.out.println(bbp.getHexadecPi());
	}
}
