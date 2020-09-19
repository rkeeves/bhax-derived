package prog2.exptree;

import prog2.tree.NodeAdapter;

public class ExpressionNodeAdapter 
implements 
NodeAdapter<ExpressionNode>
{
	
	public ExpressionNodeAdapter() {}
	@Override
	public Object getValue(ExpressionNode n) {return n.v;}
	
	@Override
	public ExpressionNode getLeft(ExpressionNode n) {return n.l;}
	
	@Override
	public ExpressionNode getRight(ExpressionNode n) {return n.r;}
	@Override
	public String getAuxData(ExpressionNode n) {
		return null;
	}
}
