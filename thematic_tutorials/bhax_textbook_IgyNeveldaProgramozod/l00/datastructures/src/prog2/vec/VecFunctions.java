package prog2.vec;

public class VecFunctions{
	

	public static int teljes_keres1(Vec<Integer> A, int ertek){
		int i = 1;
		while(i<=A.size() && A.get(i)!= ertek) {
			i = i + 1;
		}
		if(i>A.size()) {
			throw new RuntimeException("nincs ilyen erteku elem");
		}else {
			return i;
		}
	}
	
	
	public static int teljes_keres2(Vec<Integer> A, int ertek){
		for (int i = 1; i <= A.size(); i++) {
			if(A.get(i)==ertek) {
				return i;
			}
		}
		throw new RuntimeException("nincs ilyen erteku elem");
	}

	
	public static int teljes_keres_rek(Vec<Integer> A, int ertek){
		if(A.size()==0) {
			throw new RuntimeException("nincs ilyen erteku elem");
		}else if(A.get(1)==ertek) {
			return 1;
		}else {
			return teljes_keres_rek(A.range(2, A.size()+1), ertek)+1;
		}
	}
	
	
	public static int linearis_keres1(Vec<Integer> A, int ertek){
		int i = 1;
		while(i<=A.size() && A.get(i)<ertek) {
			i = i + 1;
		}
		if(i>A.size() || A.get(i)>ertek) {
			throw new RuntimeException("nincs ilyen erteku elem");
		}else {
			return i;
		}
	}
	
	
	public static int linearis_keres2(Vec<Integer> A, int ertek){
		for (int i = 1; i <= A.size(); i++) {
			if(A.get(i) == ertek) {
				return i;
			}else if(A.get(i)>ertek){
				throw new RuntimeException("Nincs ilyen erteku elem");
			}
		}
		throw new RuntimeException("Nincs ilyen erteku elem");
	}
	
	
	public static int linearis_keres_rek(Vec<Integer> A, int ertek){
		if(A.size() == 0 || A.get(1)>ertek) {
			throw new RuntimeException("Nincs ilyen erteku elem");
		}else if(A.get(1)==ertek) {
			return 1;
		}else {
			return linearis_keres_rek(A.range(2, A.size()+1), ertek) + 1;
		}
	}
	
	
	public static int binaris_keres1(Vec<Integer> A, int ertek){
		int also = 1;
		int felso = A.size();
		while(also<= felso) {
			int kozepso = (also+felso)/2;
			if(A.get(kozepso) == ertek) {
				return kozepso;
			}else if(A.get(kozepso) > ertek) {
				felso = kozepso - 1;
			}else {
				also = kozepso + 1;
			}
		}
		throw new RuntimeException("Nincs ilyen erteku elem");
	}
	
	
	public static int binaris_keres2(Vec<Integer> A, int ertek){
		if(A.size() == 0) {
			throw new RuntimeException("Nincs ilyen erteku elem");
		}
		int kozepso = ((1+A.size()) / 2);
		if(A.get(kozepso) == ertek) {
			return kozepso;
		}else if(A.get(kozepso) > ertek) {
			return binaris_keres2(A.range(1, (kozepso-1)+1),  ertek);
		}else {
			return kozepso + binaris_keres2(A.range(kozepso+1, A.size()+1),  ertek);
		}
	}
	
	
	public static int binaris_keres3(Vec<Integer> A, int ertek, int also, int felso){
		if(also > felso) {
			throw new RuntimeException("Nincs ilyen erteku elem");
		}
		int kozepso = ((also+felso) / 2);
		if(A.get(kozepso) == ertek) {
			return kozepso;
		}else if(A.get(kozepso) > ertek) {
			return binaris_keres3(A,ertek,also,kozepso-1);
		}else {
			return binaris_keres3(A,ertek,kozepso+1,felso);
		}
	}
	
	
	
	public static void min_kival_rendez(Vec<Integer> A){
		for(int i = 1; i <= A.size()-1; i++) {
			int min = i;
			for (int j = i; j <= A.size(); j++) {
				if(A.get(j)<A.get(min)) {
					min=j;
				}
			}
			A.swap(i, min);
		}
	}
	
	
	public static void max_kival_rendez(Vec<Integer> A){
		int i = A.size();
		while(i>=2) {
			int max = 1;
			for (int j = 2; j <= i; j++) {
				if(A.get(max)<A.get(j)) {
					max = j;
				}
			}
			A.swap(i, max);
			i--;
		}
	}
	
	
	public static void beszurasos_rendez(Vec<Integer> A){
		for(int i = 2; i <= A.size(); i++) {
			Integer kulcs = A.get(i);
			int j = i-1;
			while(j>=1 && A.get(j)>kulcs) {
				A.set(j+1, A.get(j));
				j--;
			}
			A.set(j+1, kulcs);
		}
	}
	
	
	public static void buborekos_rendez1(Vec<Integer> A){
		int i = A.size()-1;
		while(i>=1) {
			for (int j = 1; j <= i; j++) {
				if(A.get(j+1)<A.get(j)) {
					A.swap(j, j+1);
				}
			}
			i--;
		}
	}
	
	
	public static void buborekos_rendez2(Vec<Integer> A){
		int i = A.size()-1;
		boolean VOLT_CSERE = true;
		while(i>=1 && VOLT_CSERE) {
			VOLT_CSERE = false;
			for (int j = 1; j <= i; j++) {
				if(A.get(j+1)<A.get(j)) {
					A.swap(j, j+1);
					VOLT_CSERE = true;
				}
			}
			i--;
		}
	}
	
	
	public static void buborekos_rendez3(Vec<Integer> A){
		int i = A.size()-1;
		while(i>=1) {
			int utolso_csere = 1;
			for (int j = 1; j <= i; j++) {
				if(A.get(j+1)<A.get(j)) {
					A.swap(j, j+1);
					utolso_csere = j;
				}
			}
			i = utolso_csere -1;
		}
	}
	
	
	public static void shell_rendez1(Vec<Integer> A){
		Vec<Integer> LK = Vec.fromArray(new Integer[]{100,30,8,3,1});
		for (int k = 1; k <= LK.size(); k++) {
			int lepeskoz = LK.get(k);
			for (int eltolas = 1; eltolas <= lepeskoz; eltolas++) {
				int i = lepeskoz + eltolas;
				while(i<=A.size()) {
					int kulcs = A.get(i);
					int j = i - lepeskoz;
					while(j>=1 && A.get(j) > kulcs) {
						A.set(j+lepeskoz,A.get(j));
						j = j - lepeskoz;
					}
					A.set(j+lepeskoz, kulcs);
					i = i + lepeskoz;
				}
			}
		}
	}


	public static void shell_rendez2(Vec<Integer> A){
		int lepeskoz = 1;
		do {
			lepeskoz = 3 * lepeskoz +1;
		}while(lepeskoz<A.size());
		while(lepeskoz>1) {
			lepeskoz = (lepeskoz-1)/3;
			for (int i = lepeskoz+1; i <= A.size(); i++) {
				Integer kulcs = A.get(i);
				int j = i - lepeskoz;
				while(j>=1 && A.get(j) > kulcs) {
					A.set(j+lepeskoz,A.get(j));
					j = j -lepeskoz;
				}
				A.set(j+lepeskoz,kulcs);
			}
		}
	}
	
	
	public static void gyors_rendez(Vec<Integer> A, int also, int felso){
		if(also<felso) {
			int hatar = feloszt(A,also,felso);
			gyors_rendez(A,also,hatar-1);
			gyors_rendez(A,hatar+1,felso);
		}
	}
	
	private static int feloszt(Vec<Integer> A, int also, int felso){
		int kulcs = A.get(felso);
		int i = also - 1;
		for (int j = also; j <= felso-1; j++) {
			if(A.get(j)<=kulcs) {
				i= i + 1;
				A.swap(i, j);
			}
		}
		A.swap(i+1, felso);
		return i+1;
	}
	
	
	public static void kupac_rendez(Vec<Integer> K) {
		int i = K.size()/2;
		while(i>0) {
			sullyeszt(K,i,K.size());
			i = i -1;
		}
		i = K.size();
		while(i>1) {
			K.swap(1, i);
			i = i - 1;
			sullyeszt(K,1,i);
		}
	}
	
	public static void kupacold_vektort(Vec<Integer> K) {
		int i = K.size()/2;
		while(i>0) {
			sullyeszt(K,i,K.size());
			i = i -1;
		}
	}
	
	public static void rendezd_kupacolt_vektort(Vec<Integer> K) {
		int i = K.size();
		while(i>1) {
			K.swap(1, i);
			i = i - 1;
			sullyeszt(K,1,i);
		}
	}


	private static void sullyeszt(Vec<Integer> K, int honnan, int vege) {
		int x = K.get(honnan);
		int gyermek = honnan + honnan;
		while(gyermek<=vege) {
			if(gyermek<vege && K.get(gyermek+1) > K.get(gyermek)) {
				gyermek = gyermek+1;
			}
			if(K.get(gyermek) > x) {
				K.set(honnan, new Integer(K.get(gyermek)));
				honnan = gyermek;
				gyermek = honnan + honnan;
			}else {
				gyermek = vege + 1;
			}
		}
		K.set(honnan,x);
	}
	
	
	public static void osszefesulve_rendez(Vec<Integer> A) {
		int rendezett = 1;
		Vec<Integer> B = new Vec<Integer>(A.size());
		while(rendezett < A.size()) {
			rendezett = osszefesul_1(A,B,rendezett);
			rendezett = osszefesul_1(B,A,rendezett);
		}
	}


	private static int osszefesul_1(Vec<Integer> A, Vec<Integer> B, int rendezett) {
		int k = 1;
		do {
			int i = k;
			int j, a; j = a = k + rendezett;
			int b = a + rendezett;
			if(a>A.size()) {
				a = A.size()+1;
			}
			if(b>A.size()) {
				b = A.size() + 1;
			}
			while(i<a && j<b) {
				if(A.get(i)>A.get(j)) {
					B.set(k, A.get(j));
					j = j + 1;
				}else {
					B.set(k, A.get(i));
					i = i + 1;
				}
				k = k + 1;
			}
			while(i<a) {
				B.set(k,A.get(i));
				i = i + 1;
				k = k + 1;
			}
			while(j<b){
				B.set(k, A.get(j));
				j = j + 1;
				k = k + 1;
			}
		}while(false == (k>A.size()));
		return rendezett = rendezett + rendezett;
	}
}
