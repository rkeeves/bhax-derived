package prog2.redblack;

import prog2.tree.NodeAdapter;

public class RBNodeAdapter implements NodeAdapter<RBNode> {

	@Override
	public Object getValue(RBNode n) {
		return n.key;
	}

	@Override
	public RBNode getLeft(RBNode n) {
		return (n.l!=RBTree.NIL)?n.l:null;
	}

	@Override
	public RBNode getRight(RBNode n) {
		return (n.r!=RBTree.NIL)?n.r:null;
	}

	@Override
	public String getAuxData(RBNode n) {
		return (n.red?"r":null);
	}

}
