package prog2.objovrride;

import java.util.Arrays;
import java.util.List;

public class OverriddenObject {
	
	
	public OverriddenObject(String name, String ...features) {
		super();
		this.name = name;
		this.features = Arrays.asList(features);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((features == null) ? 0 : features.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		OverriddenObject other = (OverriddenObject) obj;
		if (features == null) {
			if (other.features != null)
				return false;
		} else if (!features.equals(other.features))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GoodObject [name=" + name + ", features=" + features + "]";
	}

	private String name;
	
	private List<String> features;
}
