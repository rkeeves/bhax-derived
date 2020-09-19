package prog2.table;

public class Record<T> {

	public Record(RecordState statusz, int kulcs, T ertek, int mutato) {
		super();
		this.statusz = statusz;
		this.kulcs = kulcs;
		this.ertek = ertek;
		this.mutato = mutato;
	}
	
	public RecordState statusz;
	public int kulcs;
	public T ertek;
	public int mutato;
	
	public Record<T> copy() {
		return new Record<T>(statusz,kulcs,ertek,mutato);
	}
	
	@Override
	public String toString() {
		return "Record [statusz=" + statusz + ", kulcs=" + kulcs + ", ertek=" + ertek + ", mutato=" + mutato + "]";
	}
	
	
}
