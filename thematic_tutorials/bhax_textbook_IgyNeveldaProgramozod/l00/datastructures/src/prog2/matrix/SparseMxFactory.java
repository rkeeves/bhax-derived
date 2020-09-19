package prog2.matrix;


import prog2.vec.Vec;

public class SparseMxFactory {
	
	public static <T> SparseMx<T> from_sor_oszlop(int max_sor, int max_oszlop,final Vec<Integer> sor,final Vec<Integer> oszlop,Vec<T> ertek){
		Vec<Integer> kov = kov_from_oszlop(oszlop);
		Vec<Integer> s = indexvector_from_data(max_sor,sor);
		Vec<Integer> o = indexvector_from_data(max_oszlop,oszlop);
		return new SparseMx<T>(sor,oszlop,ertek,kov,s,o);
	}
	
	private static Vec<Integer> kov_from_oszlop(Vec<Integer> oszlop) {
		Vec<Integer> kov = new Vec<Integer>(oszlop.size());
		kov.fill(0);
		for (int i = 1; i <= oszlop.size(); i++) {
			final Integer col = oszlop.get(i);
			int next_idx = oszlop.linear_search(i+1,(o)->(o!=null&&o==col));
			kov.set(i, next_idx);
		}
		return kov;
	}
	
	private static Vec<Integer> indexvector_from_data(int max,Vec<Integer> data) {
		Vec<Integer> ptr = new Vec<Integer>(max);
		ptr.fill(0);
		for (int i = 1; i <= max; i++) {
			final int t = i;
			int next_idx = data.linear_search((o)->(o!=null&o==t));
			ptr.set(i, next_idx);
		}
		return ptr;
	}
	
	
	public static <T> SparseMx<T> from_kov_s_o(	
			final Vec<Integer> kov,
			final Vec<Integer> s,
			final Vec<Integer> o,
			final Vec<T> ertek)
	{
		int element_count = ertek.size();
		Vec<Integer> sor = sor_from_s(element_count,s);
		Vec<Integer> oszlop = oszlop_from_kov_o(element_count,kov,o);
		return new SparseMx<T>(sor,oszlop,ertek,kov,s,o);
	}
	
	private static Vec<Integer> sor_from_s(int element_count,Vec<Integer> s) {
		Vec<Integer> sor = new Vec<Integer>(element_count);
		sor.fill(0);
		int max_sor = s.size();
		
		for (int i = 1; i <= max_sor; i++) {
			Integer idx = s.get(i);
			if(idx!=0) { sor.set(idx,i); }
			
		}
		int last = 0;
		for (int i = 1; i <= sor.size(); i++) {
			if(sor.get(i)==0) {sor.set(i,last);}
			last =  sor.get(i);
		}
		
		return sor;
	}
	
	private static Vec<Integer> oszlop_from_kov_o(int element_count,Vec<Integer> kov,Vec<Integer> o) {
		int max_oszlop = o.size();
		Vec<Integer> oszlop = new Vec<Integer>(element_count);
		oszlop.fill(0);
		{
			for (int i = 1; i <= max_oszlop; i++) {
				Integer idx = o.get(i);
				if(idx!=0) { oszlop.set(idx,i); }
			}
			
			for (int i = 1; i <= kov.size(); i++) {
				Integer kov_idx = kov.get(i);
				if(kov_idx!=0) { 
					oszlop.set(kov_idx,oszlop.get(i));
				}
			}
		}
		return oszlop;
	}
	
}
