package prog2.lzw2;

public class Node<NodeData> {

	public Node(NodeData value) {
		super();
		this.value = value;
		this.count = 1;
		this.left = null;
		this.right = null;
	}

	public Node(NodeData value, Node<NodeData> left, Node<NodeData> right) {
		super();
		this.value = value;
		this.left = left;
		this.right = right;
	}

	public NodeData getValue() {
		return value;
	}

	public int getCount() {
		return count;
	}
	
	public Node<NodeData> getLeft() {
		return left;
	}

	public Node<NodeData> getRight() {
		return right;
	}

	void setValue(NodeData value) {
		this.value = value;
	}

	void setCount(int v) {
		this.count = v;
	}
	
	void setLeft(Node<NodeData> left) {
		this.left = left;
	}

	void setRight(Node<NodeData> right) {
		this.right = right;
	}

	private NodeData value;

	private int count;
	
	private Node<NodeData> left;

	private Node<NodeData> right;
}