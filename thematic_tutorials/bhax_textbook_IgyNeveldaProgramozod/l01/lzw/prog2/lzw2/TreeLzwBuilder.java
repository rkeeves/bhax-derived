package prog2.lzw2;

public class TreeLzwBuilder<T> extends TreeBuilder<T> {

	public TreeLzwBuilder(T val_left, T val_root) {
		setRoot( new Node<T>(val_root));
		setTreep(getRoot());
		this.val_left=val_left;
	}
	
	@Override
	public TreeLzwBuilder<T> add(T value) {
		if (value == null)
			return this;
		if (value.equals(val_left)) {
			if (getTreep().getLeft() == null) {
				getTreep().setLeft(new Node<T>(value));
				setTreep(getRoot());
			} else {
				setTreep(getTreep().getLeft());
			}
		} else {
			if (getTreep().getRight() == null) {
				getTreep().setRight(new Node<T>(value));
				setTreep(getRoot());
			} else {
				setTreep(getTreep().getRight());
			}
		}
		return this;
	}
	
	private T val_left;

}
