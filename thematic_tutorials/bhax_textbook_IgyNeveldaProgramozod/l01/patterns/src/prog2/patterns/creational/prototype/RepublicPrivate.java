package prog2.patterns.creational.prototype;

public class RepublicPrivate extends RepublicSoldier{

	public RepublicPrivate(String name, String assignedJedi) {
		super(name, assignedJedi);
	}
	
	public RepublicPrivate(RepublicPrivate o) {
		super(o.getName(), o.getAssignedJedi());
	}
	

	@Override
	protected RepublicSoldier clone() {
		return new RepublicPrivate(this);
	}

	@Override
	public String getRole() {
		return "Private";
	}

	@Override
	protected void executeRegularOrder(int id) {
		System.out.println("Order "+id+" received. Commencing pew-pew!");
	}

	@Override
	protected void executeTotallyRegularNotAtAllSuspiciousOrder() {
		System.out.println("Shoot " + getAssignedJedi()+" in the back.");
	}

}
