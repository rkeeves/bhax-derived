package prog2.patterns.creational.abstractfactory;

import java.util.Arrays;
import java.util.List;

public class Entity implements QualifiableNoun {

	public Entity(String singular, String plural, boolean qualifierDetached, String...qualifiers) {
		this.singular = singular;
		this.plural = plural;
		this.qualifierDetached = qualifierDetached;
		this.qualifiers = Arrays.asList(qualifiers);
	}
	
	
	@Override
	public String generateString(boolean is_singular, int qualifier_id) {
		String noun = (is_singular) ? getSingular() : getPlural();
		String qualifier = getQualifier(qualifier_id);
		if(qualifier.length()<1)
			return noun;
		return (isQualifierDetached()) ? qualifier + " " + noun : qualifier + noun;
	}


	@Override
	public String getSingular() {
		return singular;
	}


	@Override
	public String getPlural() {
		return plural;
	}


	@Override
	public boolean isQualifierDetached() {
		return qualifierDetached;
	}
	
	private boolean isOutOfBounds(int idx) {
		return (idx >= qualifiers.size() || idx < 0);
	}
	
	@Override
	public String getQualifier(int idx) {
		return isOutOfBounds(idx) ? "" : qualifiers.get(idx);
	}

	private final String singular;
	
	private final String plural;
	
	private final boolean qualifierDetached;
	
	private final List<String> qualifiers;
	
}
