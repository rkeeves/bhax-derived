package test;

public class PolarGen2 {
	
	class Pair<T1,T2>{Pair(T1 a, T2 b){this.a=a;this.b=b;} public T1 a; public T2 b;}
    
	public PolarGen2() { store_empty = true; }
    
    public double next() {
        if(store_empty) {
        	Pair<Double, Double> p = calculate();
        	stored = p.b;
        	return p.a;
        }else {
        	store_empty = true;
        	return stored;
        }
    }
    
    private Pair<Double, Double> calculate(){
    	double u1, u2, v1, v2, w;
        do {
            u1 = Math.random();
            u2 = Math.random();
            v1 = 2*u1 - 1;
            v2 = 2*u2 - 1;
            w = v1*v1 + v2*v2;
        } while(w > 1);
        double r = Math.sqrt((-2*Math.log(w))/w);
        return new Pair<Double, Double>(r*v1,r*v2); 
    }
    
    boolean store_empty;
    double stored;
    
    public static void main(String[] args) {
    	PolarGen2 g = new PolarGen2();
        for(int i=0; i<10; ++i)
            System.out.println(g.next());
        
    }
}
    