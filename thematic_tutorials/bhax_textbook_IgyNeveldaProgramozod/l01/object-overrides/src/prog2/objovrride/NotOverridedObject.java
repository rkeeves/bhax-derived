package prog2.objovrride;

import java.util.Arrays;
import java.util.List;

public class NotOverridedObject {
	public NotOverridedObject(String name, String ...features) {
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

	public List<String> getFeatures() {
		return features;
	}

	private String name;
	
	private List<String> features;
}
