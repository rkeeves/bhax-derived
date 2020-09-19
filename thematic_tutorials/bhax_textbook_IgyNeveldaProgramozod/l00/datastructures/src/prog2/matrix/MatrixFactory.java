package prog2.matrix;

import java.util.function.Function;

import prog2.vec.Vec;

public class MatrixFactory {
	
	public Matrix new_matrix_szimmetrikus(Vec<Integer> v) {
		return compose(new_cfgv_szimmetrikus(),v);
	}
	
	public Matrix new_matrix_alsoharomszog(Vec<Integer> v) {
		return compose(new_cfgv_alsoharomszog(),v);
	}
	
	public Matrix new_matrix_felsoharomszog(Vec<Integer> v) {
		return compose(new_cfgv_felsoharomszog(),v);
	}
	
	public Matrix new_matrix_sorfolytonos(int columns,Vec<Integer> v) {
		return compose(new_cfgv_sorfolytonos(columns),v);
	}
	
	public Matrix new_matrix_oszlopfolytonos(int rows,Vec<Integer> v) {
		return compose(new_cfgv_oszlopfolytonos(rows),v);
	}
	
	public Matrix compose(Cimfuggveny cf,Vec<Integer> v) {
		Function<Integer,Integer> mxaccs = mx_accessor(v);
		return (i,j)->{
			try {
				return mxaccs.apply(cf.apply(i, j));
			}catch(Throwable e) {
				return 0;
			}
			
		};
	}
	
	private Function<Integer,Integer> mx_accessor(Vec<Integer> v) {
		return (i)->{
			try {
				return v.get(i);
			}catch(Throwable e) {
				return 0;
			}
			
		};
	}
	
	public Cimfuggveny new_cfgv_szimmetrikus() {
		return (i,j)->{
			int u = Math.max(i, j);
			int v = Math.min(i, j);
			return (u * (u-1) )/ 2 + v;
		};
	}
	
	public Cimfuggveny new_cfgv_alsoharomszog() {
		return (i,j)->{
			if(i<j)
				return 0;
			else
				return (i * (i-1) )/ 2 + j;
		};
	}
	
	public Cimfuggveny new_cfgv_felsoharomszog() {
		return (i,j)->{
			if(j<i)
				return 0;
			else
				return (j * (j-1) )/ 2 + i;
		};
	}
	
	public Cimfuggveny new_cfgv_sorfolytonos(int columns) {
		return (i,j)->{
			return (i-1)*columns+j;
		};
	}
	
	public Cimfuggveny new_cfgv_oszlopfolytonos(int rows) {
		return (i,j)->{
			return (j-1)*rows+i;
		};
	}
}
