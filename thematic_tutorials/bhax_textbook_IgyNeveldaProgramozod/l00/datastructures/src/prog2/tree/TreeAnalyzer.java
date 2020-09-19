package prog2.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class TreeAnalyzer<NodeT> {

	public TreeAnalyzer(NodeAdapter<NodeT> nodeAdapter,NodeT root) {
		this.nodeAdapter=nodeAdapter;
		this.root = wrap_children(root);
		this.root.calculate();
	}
	
	private NodeInfo wrap_children(NodeT n){
		NodeInfo ni = null;
		if(n!=null) {
			NodeT l = nodeAdapter.getLeft(n);
			NodeInfo lni = wrap_children(l);
			NodeT r = nodeAdapter.getRight(n);
			NodeInfo rni = wrap_children(r);
			ni = new NodeInfo(nodeAdapter.getValue(n),lni,rni,nodeAdapter.getAuxData(n));
		}
		return ni;
	}

	public int get_tree_height()
	{
		return (root==null) ? 0 : root.height;
	}
	
	public List<Object> get_values_preorder()
	{
		ArrayList<Object> l = new ArrayList<Object>();
		get_values_preorder_r(l,root);
		return l;
	}
	
	private void get_values_preorder_r(List<Object> l, NodeInfo n)
	{
		if(n==null)return;
		l.add(n.value);
		if(n.l!=null)get_values_preorder_r(l,n.l);
		if(n.r!=null)get_values_preorder_r(l,n.r);
	}
	
	public List<Object> get_values_inorder()
	{
		ArrayList<Object> l = new ArrayList<Object>();
		get_values_inorder_r(l,root);
		return l;
	}
	
	private void get_values_inorder_r(List<Object> l, NodeInfo n)
	{
		if(n==null)return;
		if(n.l!=null)get_values_inorder_r(l,n.l);
		l.add(n.value);
		if(n.r!=null)get_values_inorder_r(l,n.r);
	}
	
	public List<Object> get_values_postorder()
	{
		ArrayList<Object> l = new ArrayList<Object>();
		get_values_postorder_r(l,root);
		return l;
	}
	
	private void get_values_postorder_r(List<Object> l, NodeInfo n)
	{
		if(n==null)return;
		if(n.l!=null)get_values_postorder_r(l,n.l);
		if(n.r!=null)get_values_postorder_r(l,n.r);
		l.add(n.value);
	}
	
	public ArrayList<NodeInfo> collect_balanced_subtrees() {
		return collect_nodes((n)->{return n.balanced;});
	}
	
	public ArrayList<NodeInfo> collect_strictly_binary_subtrees() {
		return collect_nodes((n)->{return n.strictly_binary;});
	}
	
	public ArrayList<NodeInfo> collect_full_balanced_subtrees() {
		return collect_nodes((n)->{return n.fully_balanced;});
	}
	
	public ArrayList<NodeInfo> collect_non_min_height_subtrees() {
		return collect_nodes((n)->{return !n.is_min_height;});
	}
	
	public ArrayList<NodeInfo> collect_nodes(Predicate<NodeInfo> pred) {
		ArrayList<NodeInfo> nodes = new ArrayList<NodeInfo>(); 
		collect_nodes_r(nodes,root,pred);
		return nodes;
	}
	
	public ArrayList<NodeInfo> collect_leaves() {
		ArrayList<NodeInfo> nodes = new ArrayList<NodeInfo>(); 
		collect_nodes_r(nodes,root,(n)->(n.l==null&&n.r==null));
		return nodes;
	}
	
	public ArrayList<NodeInfo> collect_internal_nodes() {
		ArrayList<NodeInfo> nodes = new ArrayList<NodeInfo>(); 
		collect_nodes_r(nodes,root,(n)->(n.l==null^n.r==null));
		return nodes;
	}
	
	public ArrayList<NodeInfo> collect_all_nodes() {
		ArrayList<NodeInfo> nodes = new ArrayList<NodeInfo>(); 
		collect_nodes_r(nodes,root,(n)->true);
		return nodes;
	}
	
	public static ArrayList<NodeInfo> filter_only_most_count(ArrayList<NodeInfo> l)
	{
		int max = 0;
		for(NodeInfo n : l) {if(max<n.count) { max=n.count;}}
		Iterator<NodeInfo> iter = l.iterator();
		while (iter.hasNext()) {
			NodeInfo p = iter.next();
		  if (p.count<max) { iter.remove();}
		}
		return l;
	}
	
	public static ArrayList<NodeInfo> filter_having_nodes_minimum_of(ArrayList<NodeInfo> l,int node_count)
	{
		Iterator<NodeInfo> iter = l.iterator();
		while (iter.hasNext()) {
			NodeInfo p = iter.next();
		  if (p.count<node_count) { iter.remove();}
		}
		return l;
	}
	
	public static ArrayList<NodeInfo> filter_only_highest(ArrayList<NodeInfo> l)
	{
		int max = 0;
		for(NodeInfo n : l) {if(max<n.height) { max=n.height;}}
		Iterator<NodeInfo> iter = l.iterator();
		while (iter.hasNext()) {
			NodeInfo p = iter.next();
		  if (p.height<max) { iter.remove();}
		}
		return l;
	}
	private void collect_nodes_r(ArrayList<NodeInfo> nodes, NodeInfo n, Predicate<NodeInfo> pred) {
		if(n==null)return;
		if(n.l!=null)collect_nodes_r(nodes,n.l, pred);
		if(pred.test(n)) nodes.add(n);
		if(n.r!=null)collect_nodes_r(nodes,n.r, pred);
	}
	
	public void pretty_print() 
	{
		pretty_print_tree_header();
		pretty_print_tree();
	}
	
	
	public void pretty_print_tree_header() 
	{
		System.out.println("Tree Pretty Print");
		System.out.println("Syms:");
		System.out.println("b - balanced");
		System.out.println("f - fully balanced");
		System.out.println("s - strictly binary");
		System.out.println("m - is min height");
		System.out.println("h - height");
		System.out.println("c - count");
		System.out.println("v - value");
		System.out.println("[b f s m] (  h)(  c)");
	}
	
	public void pretty_print_tree() 
	{
		pretty_print_node_r(0,root);

	}
	
	private void pretty_print_node_r(int depth, NodeInfo n) 
	{
		if(n==null)return;
		if(n.r!=null)pretty_print_node_r(depth+1,n.r);
		System.out.println(toPretty(depth,n));
		if(n.l!=null)pretty_print_node_r(depth+1,n.l);
	}
	
	public static String toPretty(int depth, NodeInfo ni)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(
			String.format("[%d %d %d %d] (%3d)(%3d) " , 
			ni.balanced?1:0,
			ni.fully_balanced?1:0,
			ni.strictly_binary?1:0,
			ni.is_min_height?1:0,
			ni.height,
			ni.count));
		for (int i = 0; i < depth; i++) {sb.append("---");}
		sb.append(ni.value.toString());
		if(ni.additional!=null)sb.append(ni.additional);
		return sb.toString();
	}
	
	public void trav_inorder( Consumer<NodeInfo> cons) {
		trav_inorder_r(root,cons);
	}
	
	private void trav_inorder_r(NodeInfo node, Consumer<NodeInfo> cons) {
		if(node==null)return;
		if(node.l!=null)trav_inorder_r(node.l, cons);
		cons.accept(node);
		if(node.r!=null)trav_inorder_r(node.r, cons);
	}
	
	private NodeInfo root;
	
	private final NodeAdapter<NodeT> nodeAdapter;

	public void trav_preorder( Consumer<NodeInfo> cons) {
		trav_preorder_r(root,cons);
	}
	
	private void trav_preorder_r(NodeInfo node, Consumer<NodeInfo> cons) {
		if(node==null)return;
		cons.accept(node);
		if(node.l!=null)trav_preorder_r(node.l, cons);
		if(node.r!=null)trav_preorder_r(node.r, cons);
	}
	
	public void trav_postorder( Consumer<NodeInfo> cons) {
		trav_postorder_r(root,cons);
	}
	
	private void trav_postorder_r(NodeInfo node, Consumer<NodeInfo> cons) {
		if(node==null)return;
		
		if(node.l!=null)trav_postorder_r(node.l, cons);
		if(node.r!=null)trav_postorder_r(node.r, cons);
		cons.accept(node);
	}
	
	public Results results(){
		return new Results(collect_all_nodes().stream());
	}
}
