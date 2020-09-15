package prog2.lzw2;

import java.util.Arrays;

public class Main {

	public static <T> void pretty_print_node(Integer depth, Node<T> n) {
		StringBuilder sb = new StringBuilder();
		sb.append(depth).append(" ");
		for (int i = 0; i < depth; ++i)
			sb.append("---");
		sb.append(n.getValue().toString()).append(" (").append(n.getCount()).append(") ");
		System.out.println(sb.toString());
	}

	public static void main(String[] args) {
		TreeLzwBuilder<Character> zlwbuilder = new TreeLzwBuilder<>('0', '/');
		"01111001001001000111".chars().mapToObj((i) -> (char) i).forEach(zlwbuilder::add);
		System.out.println("[ZLW]");
		zlwbuilder.build().traverse((d) -> d, Main::pretty_print_node);
		
		BinTreeBuilder<Integer> bintreebuilder = new BinTreeBuilder<>();
		Arrays.asList(11, 6, 7, 888, 9, 6).forEach((c) -> bintreebuilder.add(c));
		System.out.println("[BIN]");
		bintreebuilder.build().traverse((d)->d,Main::pretty_print_node);
	}

}
