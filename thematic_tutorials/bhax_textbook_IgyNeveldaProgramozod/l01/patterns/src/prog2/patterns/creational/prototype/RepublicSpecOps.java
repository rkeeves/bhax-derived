package prog2.patterns.creational.prototype;

public class RepublicSpecOps extends RepublicSoldier{

	public RepublicSpecOps(String name, String assignedJedi) {
		super(name, assignedJedi);
	}

	public RepublicSpecOps(RepublicSpecOps o) {
		super(o.getName(), o.getAssignedJedi());
	}
	
	@Override
	protected RepublicSoldier clone(){
		return new RepublicSpecOps(this);
	}

	@Override
	public String getRole() {
		return "SpecOps";
	}

	@Override
	protected void executeRegularOrder(int id) {
		System.out.println("Order "+id+" received. Commencing stab-stab!");
	}

	@Override
	protected void executeTotallyRegularNotAtAllSuspiciousOrder() {
		System.out.println("Knife " + getAssignedJedi()+" in the back.");
	}

}
