package prog2.exptree;

public class ExpressionNode {
	
	public ExpressionNode(String v, ExpressionNode l, ExpressionNode r) {
		super();
		this.v = v;
		this.l = l;
		this.r = r;
	}
	public final String v;
	public ExpressionNode l;
	public ExpressionNode r;
}
