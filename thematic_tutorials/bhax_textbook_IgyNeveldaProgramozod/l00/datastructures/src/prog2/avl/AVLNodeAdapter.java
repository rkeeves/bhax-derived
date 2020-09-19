package prog2.avl;

import prog2.tree.NodeAdapter;

public class AVLNodeAdapter 
implements 
NodeAdapter<Node>
{

public AVLNodeAdapter() {}
@Override
public Object getValue(Node n) {return n.key;}

@Override
public Node getLeft(Node n) {return n.left;}

@Override
public Node getRight(Node n) {return n.right;}
@Override
public String getAuxData(Node n) {
	// TODO Auto-generated method stub
	return null;
}

}