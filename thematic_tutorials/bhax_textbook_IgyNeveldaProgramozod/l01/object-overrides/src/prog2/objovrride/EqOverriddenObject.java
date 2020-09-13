package prog2.objovrride;

import java.util.Arrays;
import java.util.List;

public class EqOverriddenObject {
	public EqOverriddenObject(String name, String ...features) {
		super();
		this.name = name;
		this.features = Arrays.asList(features);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EqOverriddenObject other = (EqOverriddenObject) obj;
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



	private String name;
	
	private List<String> features;
}
