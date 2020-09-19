package prog2.treesimple;

import prog2.tree.NodeAdapter;
import prog2.tree.NodeFactory;
import prog2.tree.NodeMutableAdapter;

public class SimpleNodeHandler 
implements 
	NodeAdapter<SimpleNode>,
	NodeMutableAdapter<SimpleNode>, 
	NodeFactory<SimpleNode, Integer>
{

	@Override
	public SimpleNode create(Integer val) {
		return new SimpleNode(val);
	}

	@Override
	public SimpleNode setLeftChild(SimpleNode parent, SimpleNode n) {
		SimpleNode t = parent.l;
		parent.l=n;
		return t;
	}

	@Override
	public SimpleNode setRightChild(SimpleNode parent, SimpleNode n) {
		SimpleNode t = parent.r;
		parent.r=n;
		return t;
	}

	@Override
	public Object getValue(SimpleNode n) {return n.v;}

	@Override
	public SimpleNode getLeft(SimpleNode n) {return n.l;}

	@Override
	public SimpleNode getRight(SimpleNode n) {return n.r;}

	@Override
	public String getAuxData(SimpleNode n) {
		// TODO Auto-generated method stub
		return null;
	}

}
