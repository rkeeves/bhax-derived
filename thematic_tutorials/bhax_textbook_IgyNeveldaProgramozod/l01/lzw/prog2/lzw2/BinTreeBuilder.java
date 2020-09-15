package prog2.lzw2;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BinTreeBuilder<T extends Comparable<T>> extends TreeBuilder<T> {

	@Override
	public BinTreeBuilder<T> add(T value) {
		if (value == null) {
			return this;
		}
		if (getRoot() == null) {
			setRoot(new Node<T>(value));
			setTreep(getRoot());
			return this;
		}
		boolean success = tryAddingNode(value);
		if(success == false) {
			add(value);
		}
		setTreep(getRoot());
		return this;
	}
	
	private boolean tryAddingNode(T value) {
		Node<T> current_treep = getTreep();
		int cmp = current_treep.getValue().compareTo(value);
		if (cmp == 0) {
			current_treep.setCount(getTreep().getCount());
			return true;
		} else if (cmp > 0) { 
			return tryAddingAsChildOfCurrent(value,getTreep()::getLeft, getTreep()::setLeft);
		} else{
			return tryAddingAsChildOfCurrent(value,getTreep()::getRight, getTreep()::setRight);
		}
	}
	
	private boolean tryAddingAsChildOfCurrent(T value, Supplier<Node<T>> getter, Consumer<Node<T>> setter) {
		Node<T> child = getter.get();
		if (child == null) {
			setter.accept(new Node<T>(value));
			return true;
		} else {
			setTreep(child);
			return false;
		}
	}
}
