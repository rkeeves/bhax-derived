package prog2.patterns.creational.prototype;

import java.util.HashMap;
import java.util.Map;

public class Hurray implements Copyable<Hurray> {

	private Map<Integer,String> values = new HashMap<>();
	
	Hurray(){
		values.put(1, "array");
	}
	
	public void add(Integer key, String value) {
		values.put(key, value);
	}
	
	public String get(String key) {
		return values.get(Integer.parseInt(key));
	}
	
	@Override
	public Hurray copy() {
		Hurray o = new Hurray();
		values.forEach((k,v)->o.add(k,v));
		return o;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hurray other = (Hurray) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}
	
	private static final String SEPARATOR = ", ";
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		values.forEach((k,v)->sb.append(k + " : " + v +SEPARATOR));
		int len = sb.length();
		if(len>SEPARATOR.length())
			sb.delete(len-SEPARATOR.length(), len);
		sb.append("}");
		return sb.toString();
	}

}
