package prog2.patterns.creational.prototype;

public abstract class RepublicSoldier implements Cloneable {

	public RepublicSoldier(String name, String assignedJedi) {
		super();
		this.name = name;
		this.assignedJedi = assignedJedi;
	}

	@Override
	protected abstract RepublicSoldier clone();

	public String getName() {
		return name;
	}

	public abstract String getRole();

	public String getAssignedJedi() {
		return assignedJedi;
	}

	public void reassignToJedi(String assignedJedi) {
		this.assignedJedi = assignedJedi;
	}

	public void executeOrder(int id) {
		System.out.println("It's "+getName());
		if(id==66) {
			executeTotallyRegularNotAtAllSuspiciousOrder();
		}else {
			executeRegularOrder(id);
		}
	};
	
	protected abstract void executeRegularOrder(int id);

	protected abstract void executeTotallyRegularNotAtAllSuspiciousOrder();
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignedJedi == null) ? 0 : assignedJedi.hashCode());
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
		RepublicSoldier other = (RepublicSoldier) obj;
		if (assignedJedi == null) {
			if (other.assignedJedi != null)
				return false;
		} else if (!assignedJedi.equals(other.assignedJedi))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	private final String name;

	private String assignedJedi;

}
