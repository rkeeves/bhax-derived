package prog2.tree;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class Results{

	public Results(Stream<NodeInfo> s) {
		this.s=s;
	}

	public long count() {
		return s.count();
	}

	
	public Results for_each(Consumer<NodeInfo> cons) {
		s.forEach(cons);
		return this;
	}
	
	public Results rendez_elemszam_asc() {
		s=s.sorted((a,b)->a.count - b.count);
		return this;
	}
	
	public Results rendez_elemszam_dsc() {
		s=s.sorted((a,b)->b.count - a.count);
		return this;
	}
	
	public Results rendez_magassag_asc() {
		s=s.sorted((a,b)->a.height - b.height);
		return this;
	}
	
	public Results rendez_magassag_dsc() {
		s=s.sorted((a,b)->b.height - a.height);
		return this;
	}
	
	public int max_magassag() {
		Optional<NodeInfo> ni = (s.max((a,b)->a.height-b.height));
		return ni.isPresent() ? ni.get().height : 0;
	}
	
	public int max_elemszam() {
		Optional<NodeInfo> ni = (s.max((a,b)->a.count-b.count));
		return ni.isPresent() ? ni.get().count : 0;
	}
	
	public Optional<NodeInfo> max(Comparator<NodeInfo> cmp) {
		return s.max(cmp);
	}
	
	public Optional<NodeInfo> first() {
		return s.findFirst();
	}
	
	public Results csak(Predicate<NodeInfo> p) {
		s = s.filter(p);
		return this;
	}
	
	public Results kiveve(Predicate<NodeInfo> p) {
		s = s.filter((o)->!p.test(o));
		return this;
	}
	
	
	public static boolean kiegyensulyozott(NodeInfo n) {
		return n.balanced;
	}
	
	public static boolean tokeletesen_kiegyensulyozott(NodeInfo n) {
		return n.fully_balanced;
	}
	
	public static boolean szigoruan_binaris(NodeInfo n) {
		return n.strictly_binary;
	}
	
	public static boolean min_magas(NodeInfo n) {
		return n.is_min_height;
	}
	
	public static boolean level(NodeInfo n) {
		return n.l==null && n.r==null;
	}
	
	public static void print_node_value(NodeInfo n) {
		System.out.println(n);
	}
	
	public static void print_node_short(NodeInfo n) {
		System.out.println(n.toShortString());
	}
	
	public static void print_node_long(NodeInfo n) {
		System.out.println(n.toLongString());
	}
	
	public Stream<NodeInfo> get() {return s;}
	
	private Stream<NodeInfo> s;
	
}
