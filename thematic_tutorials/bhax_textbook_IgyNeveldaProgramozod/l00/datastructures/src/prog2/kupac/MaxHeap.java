package prog2.kupac;

import prog2.vec.Vec;

public class MaxHeap {
	

	public static void kupacol_rek(Vec<Integer> K) {
		if( K.size()<2) return;
		for(int i = K.size()/2; i > 0; i--) {
			sullyeszt_rek(K,i,K.size());
		}
	}
	
	private static void sullyeszt_rek(Vec<Integer> K, int honnan, int vege) {
		int gyermek = 2*honnan;
		if(gyermek<vege && K.get(gyermek+1)>K.get(gyermek)) {
			gyermek=gyermek+1;
		}
		if(gyermek<=vege && K.get(gyermek)>K.get(honnan)) {
			Integer v = K.get(gyermek);
			K.set(gyermek, new Integer(K.get(honnan)));
			K.set(honnan, v);
			sullyeszt_rek(K,gyermek,vege);
		}
		
	}

	public static void beszur(Vec<Integer> K,Vec<Integer> V)
	{
		for(int i = 1; i < V.size()+1; i++)
		{
			beszur(K,V.get(i));
		}
	}
	
	public static void beszur(Vec<Integer> K,Integer x)
	{
		int i = K.size() +1;
		K.resize(i);
		while(i>1 && K.get(i/2)<x) {
			K.set(i, new Integer(K.get(i/2)));
			i=i/2;
		}
		K.set(i, x);
	}
	
	public static void kupacot_epit(Vec<Integer> K)
	{
		int i = K.size()/2;
		while(i>0) {
			szital(K,i,K.size());
			i=i-1;
		}
	}
	
	public static void szital(Vec<Integer> K,int honnan , int vege)
	{
		Integer x = K.get(honnan);
		int gyerek = 2*honnan;
		while(gyerek<=vege) {
			// Get the larger of the two children
			if( gyerek<vege && K.get(gyerek+1) > K.get(gyerek)) {
				gyerek = gyerek+1;
			}
			// is larger than parent
			if(K.get(gyerek) > x) {
				// copy child up to parent
				K.set(honnan,new Integer(K.get(gyerek)));
				honnan = gyerek;
				gyerek = 2*honnan;
			}else {
				// break out from loop
				gyerek = vege +1;
			}
		}
		K.set(honnan,new Integer(x));
	}
	
	
	

}
