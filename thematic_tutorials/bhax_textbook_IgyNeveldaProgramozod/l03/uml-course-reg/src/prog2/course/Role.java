package prog2.course;

import java.util.HashSet;
import java.util.Set;

public class Role
{
	
	private final UserData userData;
	
	private Set<Course> courses = new HashSet<Course>();

	public Role(UserData userData){
		super();
		this.userData=userData;
	}
	
	public Set<Course> getCourses() {
		return courses;
	}
	
	boolean addCourse(Course c) {
		return courses.add(c);
	}

	public UserData getUserData() {
		return userData;
	}

	@Override
	public String toString() {
		return "Role [courses=" + courses + "]";
	}


	
	
}

