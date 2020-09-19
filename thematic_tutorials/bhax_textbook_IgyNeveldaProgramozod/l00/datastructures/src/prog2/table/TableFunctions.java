package prog2.table;

import java.util.function.Function;

import prog2.vec.Vec;

public class TableFunctions {
	
	public static String statusz_to_string(RecordState s) {
		switch (s) {
		case URES:
			return "U";
		case FOLGLAT:
			return "F";
		default:
			return "T";
		}
	}
	
	public static <T> void print_table( Vec<Record<T>> TABLA) {
		System.out.println("STATUSZ KULCS  ÉRTÉK MUTATÓ");
		for (int i = 1; i <= TABLA.size(); i++) {
			Record<T> r = TABLA.get(i);
			System.out.println(
					String.format(
							"%2d : %s  %3d  %3d  %3d", 
							i,
							statusz_to_string(r.statusz),
							r.kulcs,
							r.ertek,
							r.mutato));
		}
	}
	
	public static <T> Vec<Record<T>> make_table( int size) {
		Vec<Record<T>> t = new Vec<Record<T>>(size);
		for (int i = 1; i <= size; i++) {
			t.set(i, make_record_empty());
		}
		return t;
	}
	
	public static <T> Record<T> make_record_empty() {
		 return new Record<T>(RecordState.URES, 0, null, 0);
	}
	
	public static <T> Record<T> make_record(int kulcs, T ertek) {
		 return new Record<T>(RecordState.FOLGLAT, kulcs, ertek, 0);
	}
	
	/**
	 *  Soros táblázat beszur
	 * @param <T>
	 * @param TABLA
	 * @param ujelem
	 */
	public static <T> void beszur1(Vec<Record<T>> TABLA, Record<T> ujelem) {
		int i = 1;
		while(i<= TABLA.size() && TABLA.get(i).kulcs!= ujelem.kulcs) {
			i = i + 1;
		}
		if(i<= TABLA.size()) {
			throw new RuntimeException("Mar van ilyen kulcsu elem");
		}else {
			TABLA.resize(TABLA.size()+1);
			TABLA.set(i,ujelem);
			
		}
	}
	/**
	 *  Soros táblázat torol
	 * @param <T>
	 * @param TABLA
	 * @param kulcs
	 */
	public static <T> void torol1(Vec<Record<T>> TABLA, int kulcs) {
		int i = 1;
		while(i<=TABLA.size() && TABLA.get(i).kulcs!= kulcs) {
			i = i + 1;
		}
		if(i> TABLA.size()) {
			throw new RuntimeException("Nincs ilyen kulcsu elem");
		}else {
			TABLA.set(i,TABLA.get(TABLA.size()).copy());
			TABLA.resize(TABLA.size()-1);
		}
	}
	
	/**
	 * Soros tablazat strazsa technikaval
	 * @param <T>
	 * @param TABLA
	 * @param ujelem
	 */
	public static <T> void beszur1a(Vec<Record<T>> TABLA, Record<T> ujelem) {
		TABLA.resize(TABLA.size()+1);
		TABLA.set(TABLA.size(), ujelem);
		int i = 1;
		while(TABLA.get(i).kulcs!= ujelem.kulcs) {
			i = i + 1;
		}
		if(i<TABLA.size()) {
			TABLA.resize(TABLA.size()-1);
			throw new RuntimeException("Mar van ilyen kulcsu elem");
		}
	}
	
	/**
	 * Soros tablazat strazsa technikaval
	 * @param <T>
	 * @param TABLA
	 * @param kulcs
	 */
	public static <T> void torol1a(Vec<Record<T>> TABLA, int kulcs, T nil) {
		TABLA.resize(TABLA.size()+1);
		TABLA.set(TABLA.size(),make_record(kulcs, nil));
		int i = 1;
		while(TABLA.get(i).kulcs!= kulcs) {
			i = i + 1;
		}
		TABLA.resize(TABLA.size()-1);
		if(i>TABLA.size()) {
			throw new RuntimeException("Nincs ilyen kulcsu elem");
		}else {
			TABLA.set(i, TABLA.get(TABLA.size()).copy());
			TABLA.resize(TABLA.size()-1);
		}
	}
	/**
	 * Kulcstranszformacios + nyilt cimzes
	 * @param <T>
	 * @param TABLA
	 * @param ujelem
	 */
	public static <T> void beszur3(Function<Integer,Integer> hash, Vec<Record<T>> TABLA, Record<T> ujelem) {
		int poz = hash.apply(ujelem.kulcs);
		if(TABLA.get(poz).statusz == RecordState.URES) {
			TABLA.get(poz).statusz = RecordState.FOLGLAT;
			TABLA.get(poz).kulcs = ujelem.kulcs;
			TABLA.get(poz).ertek = ujelem.ertek;
		}else if(TABLA.get(poz).statusz == RecordState.FOLGLAT && TABLA.get(poz).kulcs == ujelem.kulcs){
			throw new RuntimeException("Mar van ilyen kulcsu elem");
		}else {
			int i = (poz % TABLA.size()) + 1;
			while(i!=poz 
					&& (TABLA.get(i).statusz == RecordState.TOROLT 
						|| TABLA.get(i).statusz == RecordState.FOLGLAT
						&& TABLA.get(i).kulcs != ujelem.kulcs)) {
				i = (i % TABLA.size())+1;
			}
			if(i!= poz && TABLA.get(i).statusz == RecordState.FOLGLAT) {
				throw new RuntimeException("Mar van ilyen kulcsu elem");
			}else if(TABLA.get(poz).statusz == RecordState.FOLGLAT) {
				i = (poz % TABLA.size())+1;
				while(i!=poz && TABLA.get(i).statusz== RecordState.FOLGLAT) {
					i = (i % TABLA.size() )+1;
				}
				if(i == poz) {
					throw new RuntimeException("Betelt a tablazat");
				}
 			}else {
 				i= poz;
 			}
			TABLA.get(i).statusz = RecordState.FOLGLAT;
			TABLA.get(i).kulcs = ujelem.kulcs;
			TABLA.get(i).ertek = ujelem.ertek;
		}
	}
	
	public static <T> void torol3(Function<Integer,Integer> hash,Vec<Record<T>> TABLA, int kulcs) {
		int poz = hash.apply(kulcs);
		if(TABLA.get(poz).statusz == RecordState.URES) {
			throw new RuntimeException("Nincs ilyen kulcsu elem");
		}else if(TABLA.get(poz).statusz == RecordState.FOLGLAT && TABLA.get(poz).kulcs == kulcs) {
			TABLA.get(poz).statusz = RecordState.TOROLT;
		}else{
			int i = (poz % TABLA.size())+1;
			while(i!= poz && ( TABLA.get(i).statusz == RecordState.TOROLT
					|| TABLA.get(i).statusz == RecordState.FOLGLAT && TABLA.get(i).kulcs != kulcs
					)) {
				i = (i % TABLA.size()) +1 ;
			}
			if(i==poz || TABLA.get(i).statusz == RecordState.URES) {
				throw new RuntimeException("Nincs ilyen kulcsu elem");
			}else{
				TABLA.get(i).statusz=RecordState.TOROLT;
			}
		}
	}
	
	/**
	 * kulcstrafo belso lancolassal
	 * @param <T>
	 * @param hash
	 * @param TABLA
	 * @param ujelem
	 */
	public static <T> void beszur4a(Function<Integer,Integer> hash, Vec<Record<T>> TABLA, Record<T> ujelem) {
		int poz = hash.apply(ujelem.kulcs);int elozo,utolso;elozo=utolso=0;
		if(TABLA.get(poz).statusz==RecordState.FOLGLAT) {
			int i,elso; i = elso = hash.apply(TABLA.get(poz).kulcs);
			if(poz==elso) {
				do{
					if(TABLA.get(i).kulcs==ujelem.kulcs) {
						throw new RuntimeException("Mar van ilyen kulcsu elem");
					}
					 utolso = i;
					i = TABLA.get(i).mutato;
				}while(i!=0);
			}else {
				do {
					if(i==poz) {
						elozo = utolso;
					}
					utolso = i;
					i = TABLA.get(i).mutato;
				}while(i!=0);
			}
			i = (utolso % TABLA.size()) + 1;
			while(i!=utolso && TABLA.get(i).statusz == RecordState.FOLGLAT) {
				i = (i % TABLA.size()) + 1;
			}
			if(i==utolso) {
				throw new RuntimeException("Betelt a tablazat");
			}else if(poz == elso) {
				TABLA.get(utolso).mutato = poz = i;
			}else {
				TABLA.set(i, TABLA.get(poz).copy());
				TABLA.get(elozo).mutato = i;
			}
		}
		TABLA.get(poz).kulcs = ujelem.kulcs;
		TABLA.get(poz).ertek = ujelem.ertek;
		TABLA.get(poz).statusz = RecordState.FOLGLAT;
		TABLA.get(poz).mutato = 0;
	}
	
	
	public static <T> void torol4a(Function<Integer,Integer> hash,Vec<Record<T>> TABLA, int kulcs) {
		int poz = hash.apply(kulcs);
		if(TABLA.get(poz).statusz!=RecordState.FOLGLAT
				|| hash.apply(TABLA.get(poz).kulcs)!=poz) {
			throw new RuntimeException("Nincs ilyen kulcsu elem");
		}
		int elozo = 0;
		while(poz!=0 && TABLA.get(poz).kulcs!= kulcs) {
			elozo = poz;
			poz = TABLA.get(poz).mutato;
		}
		if(poz==0) {
			throw new RuntimeException("Nincs ilyen kulcsu elem");
		}else if(elozo!=0) {
			TABLA.get(elozo).mutato = TABLA.get(poz).mutato;
		}else if(TABLA.get(poz).mutato!=0) {
			elozo = poz;
			poz = TABLA.get(poz).mutato;
			TABLA.set(elozo,TABLA.get(poz).copy());
		}
		TABLA.get(poz).statusz = RecordState.TOROLT;
	}
}
