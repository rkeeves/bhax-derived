package prog2.redblack;


public abstract class RBTree {

	public static final RBNode NIL = mk_nil_node();
	
	public int rot_r_counter = 0;
	public boolean count_rot_r = true;
	
	public int rot_l_counter = 0;
	public boolean count_rot_l = true;
	
	public int piros_fekete_beszur8_counter = 0;
	public boolean count_piros_fekete_beszur8 = true;
	
	public int piros_fekete_beszur10_counter = 0;
	public boolean count_piros_fekete_beszur10 = true;
	
	protected RBNode root = mk_root();

	public RBTree() {}
	
	
	protected static final RBNode mk_nil_node(){
		RBNode nil = new RBNode();
		nil.l=nil.r=nil.p=nil;
		nil.red = false;
		nil.key=null;
		return nil;
	}
	
	protected static final RBNode mk_root(){
		RBNode n = new RBNode();
		n.l=n.r=n.p=NIL;
		n.red=false;
		return n;
	}
	
	protected static final RBNode mk_node(int v){
		RBNode n = new RBNode();
		n.key=v;
		n.l=n.r=n.p=NIL;
		n.red=true;
		return n;
	}
	
	public RBNode get_root() {return root;};
	
	public void insert(int v) {
		RBNode n;
		if(root.key==null) {
			root.key=v;
			n = root;
		}else{
			n = bin_tree_insert(v);
			fix_insert(n);
		}
	}
	
	private RBNode bin_tree_insert(int v)
	{
		
		RBNode y = NIL;
		RBNode x = root;
		while(x!=NIL) {
			y=x;
			if(v==x.key)
				throw new RuntimeException("Value "+v+" already exists!");
			else if(v<x.key) {
				if(count_piros_fekete_beszur8)piros_fekete_beszur8_counter++;
				x=x.l;
			}else {
				if(count_piros_fekete_beszur10)piros_fekete_beszur10_counter++;
				x=x.r;
			}
		};
		RBNode n = mk_node(v);
		n.p=y;
		if(y==NIL)
			root = n;
		else if(n.key<y.key)
			y.l = n;
		else
			y.r = n;
		return n;
		
	}
	
	protected void rot_left(RBNode x) {
		if(count_rot_l) {rot_l_counter++;}
		RBNode y = x.r;
		x.r = y.l;
		if(y.l!=NIL) {
			y.l.p=x;
		}
		y.p=x.p;
		if(x.p==NIL||x.p==null) {
			root = y;
		}else {
			if(x==x.p.l)
				x.p.l=y;
			else
				x.p.r=y;
		}
		y.l=x;
		x.p=y;
	}
	
	protected void rot_right(RBNode x){
		if(count_rot_r)rot_r_counter++;
		RBNode y = x.l;
		x.l = y.r;
		if(y.r!=NIL) {
			y.r.p=x;
		}
		y.p=x.p;
		if(x.p==NIL||x.p==null) {
			root = y;
		}else {
			if(x==x.p.r)
				x.p.r=y;
			else
				x.p.l=y;
		}
		y.r=x;
		x.p=y;
	}
	
	protected abstract void fix_insert(RBNode n);
	
	public RBNode find(int v)
	{
		RBNode x = root;
		while(x!=NIL) {
			if(x.key==v)return x;
			x=(x.key>v)?x.l:x.r;
		}
		return x;
	}
	
	public void delete(int v)
	{
		RBNode n = find(v);
		if(n==NIL)throw new RuntimeException("Value "+v+" was not found");
		delete(n);
	}
	
	public RBNode min(RBNode x) {
		while(x.l!=NIL) {
			x=x.l;
		}
		return x;
	}
	
	
	public RBNode max(RBNode x) {
		while(x.r!=NIL) {
			x=x.r;
		}
		return x;
	}
	public RBNode succ(RBNode x) {
		if(x.r!=NIL)
			return min(x.r);
		RBNode y = x.p;
		while(y!=NIL&&x==y.r) {
			x=y;
			y=y.p;
		}
		return y;
	}
	
	public void delete(RBNode z) {
		RBNode y;
		if(z.l==NIL||z.r==NIL) {
			y=z;
		}else {
			y=succ(z);
		}
		RBNode x=(y.l!=NIL)?y.l:y.r;
		x.p=y.p;
		if(y.p==NIL)
			root=x;
		else if(y==y.p.l)
			y.p.l=x;
		else
			y.p.r=x;
		if(y!=z)
			z.key=y.key;
		if(y.red==false) {
			delete_fix3(x);
		}
			
	}
	private void delete_fix3(RBNode x)
	{
	    RBNode s;
	    while (x != root && x.red == false) {
	      if (x == x.p.l) {
	        s = x.p.r;
	        if (s.red == true) {
	          s.red = false;
	          x.p.red = true;
	          rot_left(x.p);
	          s = x.p.r;
	        }

	        if (s.l.red == false && s.r.red == false) {
	          s.red = true;
	          x = x.p;
	        } else {
	          if (s.r.red == false) {
	            s.l.red = false;
	            s.red = true;
	            rot_right(s);
	            s = x.p.r;
	          }

	          s.red = x.p.red;
	          x.p.red = false;
	          s.r.red = false;
	          rot_left(x.p);
	          x = root;
	        }
	      } else {
	        s = x.p.l;
	        if (s.red == true) {
	          s.red = false;
	          x.p.red = true;
	          rot_right(x.p);
	          s = x.p.l;
	        }

	        if (s.r.red == false && s.r.red == false) {
	          s.red = true;
	          x = x.p;
	        } else {
	          if (s.l.red == false) {
	            s.r.red = false;
	            s.red = true;
	            rot_left(s);
	            s = x.p.l;
	          }

	          s.red = x.p.red;
	          x.p.red = false;
	          s.l.red = false;
	          rot_right(x.p);
	          x = root;
	        }
	      }
	    }
	    x.red = false;
  }
	
}
