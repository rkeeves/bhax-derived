package prog2.vec;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Predicate;

public class Vec<T> {

	public static <T> Vec<T> fromArray(T[] arr) {
		return new Vec<T>(arr);
	}

	public Vec(T[] arr, int from, int to)
	{
		this.arr= Arrays.copyOfRange(arr, from, to);
	}
	
	public Vec(T[] arr)
	{
		this.arr= Arrays.copyOf(arr, arr.length);
	}
	
	@SuppressWarnings("unchecked")
	public Vec(int size)
	{
		this.arr= (T[]) Array.newInstance(Object.class, size);
	}

	public void fill(T val)
	{
		Arrays.fill(this.arr, val);	
	}
	public int size(){return arr.length;}
	
	public void resize(int newsize)
	{
		arr = Arrays.copyOf(arr, newsize);
	}
	
	public void check_bounds(int i)
	{
		if(i>=arr.length || i < 0) throw new IndexOutOfBoundsException();
	}
	
	public T get(int i)
	{
		check_bounds(--i);
		return arr[i];
	}
	
	public T set(int i, T o)
	{
		check_bounds(--i);
		T t = arr[i];
		arr[i] = o;
		return t;
	}
	
	public T[] to_array()
	{
		return Arrays.copyOf(arr, arr.length);
	}
	
	public void swap(int a, int b)
	{
		T o = this.get(a);
		this.set(a,this.get(b));
		this.set(b,o);
	}
	
	public Vec<T> range(int from, int to)
	{
		return new Vec<T>(arr, --from, --to);
	}
	
	public Vec<T> get_copy()
	{
		return new Vec<T>(arr);
	}
	
	
	@Override
	public String toString() {
		return "Vec " + Arrays.toString(arr);
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(arr);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vec other = (Vec) obj;
		if (!Arrays.deepEquals(arr, other.arr))
			return false;
		return true;
	}


	public T[] get_arr() {
		return arr;
	}

	public int linear_search(Predicate<T> pred) {
		return linear_search(1,arr.length,pred);
	}
	
	public int linear_search(int from,Predicate<T> pred) {
		return linear_search(from,arr.length,pred);
	}
	
	public int linear_search(int from, int to_inclusive,Predicate<T> pred) {
		for (int i = (from-1); i <= (to_inclusive-1); i++) {
			if(pred.test(arr[i])) return (i+1);
		}
		return 0;
	}
	
	private T arr[];
}