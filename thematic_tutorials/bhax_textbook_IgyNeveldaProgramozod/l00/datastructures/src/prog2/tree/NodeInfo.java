package prog2.tree;


public class NodeInfo{
	
	NodeInfo(Object value,NodeInfo l,NodeInfo r, String additional)
	{
		this.value=value;
		this.l=l;
		this.r=r;
		this.additional=additional;
	}
	
	public Object value;
	
	public NodeInfo l = null;
	
	public NodeInfo r = null;

	public int count = 0;
	public int height = 0;
	public boolean balanced = true;
	public boolean fully_balanced = true;
	public boolean strictly_binary= true;
	public boolean is_min_height= true;
	public String additional = null;
	
	public void calculate()
	{
		calculate_count();
		calculate_height();
		calculate_balanced();
		calculate_fully_balanced();
		calculate_strictly_binary();
		calculate_min_height();
	}
	

	private int calculate_count()
	{
		count = 1;
		if(l!=null) count += l.calculate_count();
		if(r!=null) count += r.calculate_count();
		return count;
	}
	
	private int calculate_height()
	{
		int h0 = 0;
		int h1 = 0;
		if(l!=null) h0 = l.calculate_height();
		if(r!=null) h1 = r.calculate_height();
		h0 = Math.max(h0, h1);
		return height = (h0>=0) ? h0+1 : 1;
	}
	

	private boolean calculate_balanced() {
		int t0 =0;
		int t1 =0;
		boolean left_balanced = true;
		boolean right_balanced = true;
		if(l!=null) {
			left_balanced = l.calculate_balanced();
			t0 = l.height;
		}
		if(r!=null) {
			right_balanced = r.calculate_balanced();
			t1 = r.height;
		}
		balanced = (left_balanced&&right_balanced) ? (Math.abs(t0-t1)<=1) : false;
		return balanced;
	}
	
	private boolean calculate_fully_balanced() {
		int t0 = 0;
		int t1 = 0;
		boolean left_balanced = true;
		boolean right_balanced = true;
		if(l!=null) {
			left_balanced = l.calculate_fully_balanced();
			t0 = l.count;
		}
		if(r!=null) {
			right_balanced = r.calculate_fully_balanced();
			t1 = r.count;
		}
		return fully_balanced = (right_balanced&&left_balanced) ? (Math.abs(t0-t1)<=1) : false;
	}
	
	private boolean calculate_strictly_binary()
	{
		boolean lsb = true;
		boolean rsb = true;
		if(l!=null) {lsb=l.calculate_strictly_binary();}
		if(r!=null) {rsb=r.calculate_strictly_binary();}
		if(l==null^r==null) {return strictly_binary=false;}
		else{ return strictly_binary =(lsb&&rsb);}
	}
	
	private boolean calculate_min_height()
	{
		if(height==0) return is_min_height = true;
		if(l!=null) {l.calculate_min_height();}
		if(r!=null) {r.calculate_min_height();}
		int smaller_count = (int) (Math.pow(2, height)-1); 
		return is_min_height = (smaller_count<count);
	}
	
	@Override
	public String toString() {
		return  "(" + value.toString() + ")";
	}

	public String toShortString() {
		return "value=" + value + ", count="+count+", height="+height;
	}
	
	public String toLongString() {
		return "value=" + value + ", count=" + count + ", height=" + height
				+ ", balanced=" + balanced + ", fully_balanced=" + fully_balanced + ", strictly_binary="
				+ strictly_binary + ", is_min_height=" + is_min_height + ", additional=" + additional + "]";
	}

	
	
	
	
}
