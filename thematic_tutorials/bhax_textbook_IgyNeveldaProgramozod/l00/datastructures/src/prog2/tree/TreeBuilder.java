package prog2.tree;


import java.util.HashMap;


public class TreeBuilder<NodeT, V>{
	

	public TreeBuilder(NodeMutableAdapter<NodeT> mut, NodeFactory<NodeT, V> fact)
	{
		this.mut=mut;
		this.fact=fact;
	}

	
	public TreeBuilder<NodeT, V> n(V v){return new_node(v);}
	
	public TreeBuilder<NodeT, V> n(V... vs){
		for (V v : vs) {
			if(v==null) {
				s();
			}else {
				new_node(v);
			}
		}
		return this;
	}
	
	
	public TreeBuilder<NodeT, V> new_node(V v)
	{
		
		NodeT n = fact.create(v);
		NodeT parent = m.get(parent_of(idx));
		if(parent!=null) {
			if(is_left_child(idx)) {
				mut.setLeftChild(parent, n);
			}else {
				mut.setRightChild(parent, n);
			}
		}
		m.put(idx, n);
		idx++;
		return this;
	}
	
	public TreeBuilder<NodeT,V> s(){return skip(1);}
	
	public TreeBuilder<NodeT,V> s(int num){return skip(num);}
	
	public TreeBuilder<NodeT,V> skip(int num)
	{
		idx+=num;
		return this;
	}
	
	public NodeT build()
	{
		return m.get(0);
	}
	
	private int parent_of(int child){return (child-1)/2;}
	
	private boolean is_left_child(int child){return child%2==1;}
	
	
	private int idx = 0;
	
	private final NodeMutableAdapter<NodeT> mut;
	
	private final NodeFactory<NodeT, V> fact;
	
	private HashMap<Integer,NodeT> m = new HashMap<Integer,NodeT>();
	
}
