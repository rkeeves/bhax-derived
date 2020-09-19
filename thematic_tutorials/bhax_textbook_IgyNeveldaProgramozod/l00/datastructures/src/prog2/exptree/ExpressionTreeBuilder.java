package prog2.exptree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class ExpressionTreeBuilder {

	public ExpressionNode from_postfix(String postfix) {
		return parse_postfix(tokenize(postfix));
	}
	
	public ExpressionNode from_prefix(String prefix) {
		return parse_prefix(tokenize(prefix));
	}
	
	private List<Token> tokenize(String src){
		List<Token> tokens = new ArrayList<Token>();
		for (StringTokenizer iter = new StringTokenizer(src); iter.hasMoreTokens();) {
			String tok = iter.nextToken();
			tokens.add(new Token(is_int(tok),tok));
		}
		return tokens;
	}
	
	private ExpressionNode parse_postfix(List<Token> tokens) {
		Stack<ExpressionNode> st = new Stack<ExpressionNode>();
		for (Token tok : tokens) {
			if(tok.numeric) {
				st.push(new ExpressionNode(tok.s,null,null));
			}else {
				ExpressionNode r = st.pop();
				ExpressionNode l = st.pop();
				st.push(new ExpressionNode(tok.s,l,r));
			}
		}
		if(st.size()!=1) throw new RuntimeException("Stack is not of size 1");
		return st.pop();
	}
	
	
	private boolean is_int(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}catch(Throwable e) {
			return false;
		}
	}
	
	private ExpressionNode parse_prefix(List<Token> tokens) {
		Stack<ExpressionNode> st = new Stack<ExpressionNode>();
		for (int i = tokens.size()-1; i >= 0; i--) {
			Token tok = tokens.get(i);
			if(tok.numeric) {
				st.push(new ExpressionNode(tok.s,null,null));
			}else {
				ExpressionNode l = st.pop();
				ExpressionNode r = st.pop();
				st.push(new ExpressionNode(tok.s,l,r));
			}
		}
		if(st.size()!=1) throw new RuntimeException("Stack is not of size 1");
		return st.pop();
	}
}
