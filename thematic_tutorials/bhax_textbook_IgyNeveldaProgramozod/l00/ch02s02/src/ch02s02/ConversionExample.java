package ch02s02;

public class ConversionExample {

	public static int add(Integer...integers) {
		int accu = 0;
		for (Integer integer : integers) {
			if(integer!=null) {
				accu+=integer;
			}
		}
		return accu;
	}
	
	public static void main(String[] args) {
		System.out.println(1 + 2 + "aa");
	}

}
