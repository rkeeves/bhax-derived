package prog2.redblack;


public class CLRSTree extends RBTree {

	@Override
	protected void fix_insert(RBNode x) {
		RBNode u;
		x.red=true;
		while(x!=root&&x.p.red) {
			if(x.p.p.l==x.p) {
				u = x.p.p.r;
				if(u.red) {
					x.p.red = u.red=false;
					x.p.p.red=true;
					x = x.p.p;
				}else {
					if(x==x.p.r){
						x = x.p;
						rot_left(x);
					}
					x.p.red = false;
					x.p.p.red = true;
					rot_right(x.p.p);
				}
			}else {
				u = x.p.p.l;
				if(u.red) {
					x.p.red = u.red=false;
					x.p.p.red=true;
					x = x.p.p;
				}else {
					if(x==x.p.l){
						x = x.p;
						rot_right(x);
					}
					x.p.red = false;
					x.p.p.red = true;
					rot_left(x.p.p);
				}
			}
		}
		root.red=false;
	}
	

}
