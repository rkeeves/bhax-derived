package prog2.course;

public class StudentRole extends Role
{

	public StudentRole(UserData userData){
		super(userData);
	}

	@Override
	public String toString() {
		return "StudentRole -> " + super.toString();
	}

}

