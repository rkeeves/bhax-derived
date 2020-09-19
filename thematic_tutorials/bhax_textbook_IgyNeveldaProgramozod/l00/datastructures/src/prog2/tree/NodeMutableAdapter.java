package prog2.tree;

public interface NodeMutableAdapter<NodeT> {
	NodeT setLeftChild(NodeT parent, NodeT n);
	NodeT setRightChild(NodeT parent, NodeT n);
}
