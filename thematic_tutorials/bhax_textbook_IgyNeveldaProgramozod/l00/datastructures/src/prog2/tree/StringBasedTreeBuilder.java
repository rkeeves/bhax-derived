package prog2.tree;

import java.util.StringTokenizer;

public class StringBasedTreeBuilder<NodeT> {
	
	public StringBasedTreeBuilder( TreeBuilder<NodeT, Integer> builder){
		this.builder=builder;
	}
	
	public static final String EMPTY_SYM = "x"; 
	
	public StringBasedTreeBuilder<NodeT> push(String s) {
		StringTokenizer tok = new StringTokenizer(s);
		while(tok.hasMoreTokens()) {
			String t = tok.nextToken();
			try {
				builder.n(Integer.parseInt(t));
			}catch(Exception e) {
				if(t.startsWith(EMPTY_SYM))
					builder.s();
			}
		}
		return this;
	}
	
	private final TreeBuilder<NodeT, Integer> builder;
}
