package prog2.course;

public class Main {
	
	Main(){
		
	}

	private static void pretty_print(Course c) {
		System.out.println(c);
		c.getLecturers().forEach(staff->System.out.println(staff.getUserData()));
		c.getStudents().forEach(student->System.out.println(student.getUserData()));
	}
	public static void main(String[] args) {
		UserData lec = new UserData("Lecturer", "Tamas");
		UserData msc = new UserData("MSC", "Tamas");
		UserData stud1 = new UserData("A", "A");
		UserData stud2 = new UserData("B", "B");
		
		Course c01 = new Course("inbp-01", 99, CourseKind.LECTURE);
		c01.addLecturer(lec.getStaffRole());
		c01.addStudent(msc.getStudentRole());
		
		Course c02 = new Course("inbp-02", 99, CourseKind.SEMINAR);
		c02.addLecturer(msc.getStaffRole());
		c02.addStudent(stud1.getStudentRole());
		c02.addStudent(stud2.getStudentRole());
		
		pretty_print(c01);
		pretty_print(c02);
	}
}
