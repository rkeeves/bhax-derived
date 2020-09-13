import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * Looks ugly af, but I wanted to mimic our c++ solution as closely as possible.
 * This is terrible in a lot of sense.
 * For me, the fact that BinTree requires T to be Comparable is a crime against humanity,
 * but the task wass to directly copy the c++ src, so here we go folks!
 */
public class BaseTree<T> {
	class Node{

		Node(T val) {
			super();
			this.val = val;
			this.count = 1;
			this.left = null;
			this.right = null;
		}
		
		
		public Node(T val, BaseTree<T>.Node left, BaseTree<T>.Node right) {
			super();
			this.val = val;
			this.count = 1;
			this.left = left;
			this.right = right;
		}

		Node copy(){
			return new Node(this.val, 
					(this.left == null ? null : this.left.copy()), 
					(this.right == null ? null : this.right.copy()));
		}
		
		T val;
		
		int count;
		
		Node left;
		
		Node right;
	}
	
	BaseTree(){
		this.root = null;
		this.treep = null;
	}
	
	BaseTree(T val){
		this.root = new Node(val);
		this.treep = root;
	}
	
	BaseTree(Node root, Node treep){
		this.root = root;
		this.treep = treep;
	}

	public BaseTree<T> copy(){
		if(this.root == null)
			return new BaseTree<T>(null,null);
		else
			return new BaseTree<T>(root.copy(),null);
	}
	
	
	
	Node cp(Node node, Node treep) 
    {
        Node newNode = null;
        
        if(node != null)
        {
            newNode = new Node(node.val);
            
            newNode.left = cp(node.left, treep);            
            newNode.right = cp(node.right, treep); 
            newNode.count = node.count;
            
            if(node == treep)
                this.treep = newNode;
        }
        
        return newNode;
    }

	public void traverse(BiConsumer<Integer,Node> cons) {
		traverse(0, this.root, cons);
	}
	
	private void traverse(int depth, Node n, BiConsumer<Integer,Node> user_fun) {
		if(n != null){
	        traverse(depth+1, n.right, user_fun);
	        user_fun.accept(depth,n);
	        traverse(depth+1, n.left, user_fun);
	    }
	}
	
	public void pretty_print_tree() {
		traverse(1, this.root, this::pretty_print_node);
	}
	
	public void pretty_print_node(Integer depth, Node n) {
		StringBuilder sb = new StringBuilder();
		sb.append(depth)
		.append(" ");
		for(int i = 0; i < depth; ++i)
			sb.append("---");
		sb.append(n.val.toString())
		.append(" (")
		.append(n.count)
		.append(") ");
        System.out.println(sb.toString()); 
	}
	
	
	
	protected Node root;
	
	protected Node treep;
	
	
	public static class ZLWTree<T> extends BaseTree<T>{

		public ZLWTree(T val_root, T val_left) {
			super(val_root);
			this.val_left=val_left;
		}
		
		public ZLWTree<T> add(T value)
		{
			if(value == null)
				return this;
			if(value.equals(val_left)) {
		        if(treep.left == null) {
		        	treep.left = new Node(value);
		            treep = root;
		        } else {
		            treep = treep.left; 
		        }
		    } else {
		        if(treep.right == null) {
		        	treep.right = new Node(value);
		            treep = root;
		        } else {
		            treep = treep.right; 
		        }
		        
		    }
		    return this;
		}
		
		private T val_left;
	}
	
	public static class BinTree<T extends Comparable<T>> extends BaseTree<T>{
		
		public BinTree() {
			super();
		}
		
		
		public BinTree<T> add(T value)
		{
			if(value == null)
				 return this;
		
			if(treep == null) {
		        root = treep = new Node(value);
		    } else {
		    	int cmp = treep.val.compareTo(value);
		    	if(cmp == 0) {
		    		 treep.count++;
		    	}else if(cmp > 0) {
		    		if(treep.left == null) {
		    			treep.left = new Node(value);
			        } else {
			            treep = treep.left;
			            this.add(value);
			        }
		    	}else if(cmp < 0) {
		    		if(treep.right == null) {
			            treep.right = new Node(value);
			        } else {
			            treep = treep.right;
			            this.add(value);
			        }
		    	}
		    }
			treep = root;

		    return this;
		}
		
	}
	
	public static void main(String[] args) {
		
		System.out.println("ZLW Tree");
		ZLWTree<Character> zlw = new ZLWTree<>('/','0');
		"01111001001001000111"
			.chars()
			.mapToObj((i)->(char) i)
			.forEach(zlw::add);
		zlw.pretty_print_tree();
		
		System.out.println("Bin Tree");
		BinTree<Integer> bt = new BinTree<>();
		Arrays.asList(11,6,7,888,9,6).forEach((c)->bt.add(c));
		bt.traverse(bt::pretty_print_node);
	}
}
