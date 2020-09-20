package prog2.patterns.creational.prototype;

import java.util.HashMap;
import java.util.Map;

public class Entity implements Copyable<Entity>{

	private Map<String, String> attributes = new HashMap<>();
	
	private static int next_id = 0;
	
	private final int id;
	
	Entity() {
		id = next_id++;
	}
	
	public void add(String key, String value) {
		if(key.equalsIgnoreCase("id"))
			return;
		attributes.put(key, value);
	}
	
	public String get(String key) {
		if(key.equalsIgnoreCase("id"))
			return ""+id;
		return attributes.get(key);
	}
	
	@Override
	public Entity copy() {
		Entity o = new Entity();
		attributes.forEach((k,v)->o.add(k, v));
		return o;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
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
		Entity other = (Entity) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		return true;
	}

	private static final String SEPARATOR = ", ";
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id " + " : " + id + SEPARATOR);
		attributes.forEach((k,v)->sb.append(k + " : " + v +SEPARATOR));
		int len = sb.length();
		if(len>SEPARATOR.length())
			sb.delete(len-SEPARATOR.length(), len);
		sb.append("}");
		return sb.toString();
	}
	
	

}
