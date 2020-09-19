package prog2.redblack;


public class OkasakiTree extends RBTree {

	@Override
	protected void fix_insert(RBNode z) {
		while(z.p.red) {
			if(z.p==z.p.p.l) {
				if(z==z.p.r) {
					z=z.p;
					rot_left(z);
				}
				z.red=false;
				z.p.red=true;
				z=z.p.p;
				rot_right(z);
				//I added this
				z=z.p;
			}else{
				if(z==z.p.l) {
					z=z.p;
					rot_right(z);
				}
				z.red=false;
				z.p.red=true;
				z=z.p.p;
				rot_left(z);
				//I added this
				z=z.p;
			}
		}
		root.red=false;
	}

}
