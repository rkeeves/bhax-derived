package prog2.tree;

public interface NodeFactory<NodeT,V> {
	NodeT create(V val);

}
