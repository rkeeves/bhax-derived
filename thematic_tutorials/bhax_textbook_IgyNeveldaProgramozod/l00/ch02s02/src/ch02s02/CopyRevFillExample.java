package ch02s02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CopyRevFillExample {

	public static void print(String msg, List<?> l0, List<?> l1){
		System.out.println("["+msg+"]");
		l0.stream().forEach((i)->System.out.print(i+" "));
		System.out.print(" --> ");
		l1.stream().forEach((i)->System.out.print(i+" "));
		System.out.println();
	}
	
	public static void main(String[] args) {
		List<Integer> current = Arrays.asList(1,2,3,4,5,6,7,8,9);
		List<Integer> last = new ArrayList<>(current);
		Collections.copy(last, current);
		Collections.reverse(current);
		print("REV",last,current);
		Collections.copy(last, current);
		Collections.fill(current,0);
		print("FILL",last,current);
	}

}
