package prog2.tree;

public interface NodeAdapter<NodeT> {
	Object getValue(NodeT n);
	NodeT getLeft(NodeT n);
	NodeT getRight(NodeT n);
	String getAuxData(NodeT n);
}
