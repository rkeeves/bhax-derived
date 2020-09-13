package prog2.objovrride;

import java.util.Arrays;
import java.util.List;

public class HashOverriddenObject {
	public HashOverriddenObject(String name, String ...features) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((features == null) ? 0 : features.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	private String name;
	
	private List<String> features;
}
