package prog2.course;

public class StaffRole extends Role
{
	
	public StaffRole(UserData userData){
		super(userData);
	}

	@Override
	public String toString() {
		return "StaffRole -> " + super.toString();
	}

}

