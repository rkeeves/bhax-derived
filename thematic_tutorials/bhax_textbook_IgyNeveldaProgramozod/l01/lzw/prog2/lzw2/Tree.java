package prog2.lzw2;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class Tree<T> {

	protected Tree(Node<T> root) {
		this.root = root;
	}

	public <UserValue> void traverse(Function<Integer, UserValue> userFuncOnNewDepthLevel,
			BiConsumer<UserValue, Node<T>> userFuncOnNode) {
		traverse(0, this.root, userFuncOnNewDepthLevel, userFuncOnNode);
	}

	private <UserValue> void traverse(int depth, Node<T> n, Function<Integer, UserValue> userFuncOnNewDepthLevel,
			BiConsumer<UserValue, Node<T>> userFuncOnNode) {
		if (n != null) {
			traverse(depth + 1, n.getRight(), userFuncOnNewDepthLevel, userFuncOnNode);
			userFuncOnNode.accept(userFuncOnNewDepthLevel.apply(depth), n);
			traverse(depth + 1, n.getLeft(), userFuncOnNewDepthLevel, userFuncOnNode);
		}
	}

	private final Node<T> root;

}