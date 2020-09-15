package prog2.lzw2;

public abstract class TreeBuilder<T> {

	public abstract TreeBuilder<T> add(T value);

	Tree<T> build() {
		return new Tree<T>(this.temp_root);
	}

	protected Node<T> getTreep() {
		return treep;
	}

	protected void setTreep(Node<T> treep) {
		this.treep = treep;
	}

	protected Node<T> getRoot() {
		return temp_root;
	}

	protected void setRoot(Node<T> temp_root) {
		this.temp_root = temp_root;
	}

	private Node<T> treep;

	private Node<T> temp_root;
}
