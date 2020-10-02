package prog2.course;

public class UserData
{

	private String fname;
	
	private String lname;
	
	private final StudentRole studentRole = new StudentRole(this);
	
	private final StaffRole staffRole = new StaffRole(this);
	
	public UserData(String fname, String lname){
		super();
		this.fname=fname;
		this.lname=lname;
	}
	
	public String getFname() {
		return fname;	
	}
	
	public String getLname() {
		return lname;	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
		return result;
	}

	
	
	public StudentRole getStudentRole() {
		return studentRole;
	}

	public StaffRole getStaffRole() {
		return staffRole;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserData other = (UserData) obj;
		if (fname == null) {
			if (other.fname != null)
				return false;
		} else if (!fname.equals(other.fname))
			return false;
		if (lname == null) {
			if (other.lname != null)
				return false;
		} else if (!lname.equals(other.lname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserData [fname=" + fname + ", lname=" + lname + "]";
	}
}

