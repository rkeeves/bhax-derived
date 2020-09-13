package prog2.patterns.creational.abstractfactory;

public interface Qualifiable {

	
	boolean isQualifierDetached();

	String getQualifier(int id);
	
	String generateString(boolean is_singular, int qualifier_id);
}
