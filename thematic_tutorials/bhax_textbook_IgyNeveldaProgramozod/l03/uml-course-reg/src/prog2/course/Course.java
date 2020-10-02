package prog2.course;

import java.util.HashSet;
import java.util.Set;

public class Course
{
	
	private final String code;
	
	private final int credits;
	
	private final CourseKind courseKind;
	
	private Set<StaffRole> lecturers = new HashSet<>();
	
	private Set<StudentRole> students = new HashSet<>();
	
	
	public Course(String code, int credits, CourseKind courseKind) {
		super();
		this.code = code;
		this.credits = credits;
		this.courseKind = courseKind;
	}

	
	public String getCode() {
		return code;	
	}
	
	public Set<StaffRole> getLecturers() {
		return lecturers;	
	}
	
	public int getCredits() {
		return credits;	
	}
	
	public CourseKind getCourseKind() {
		return courseKind;	
	}
	
	public Set<StudentRole> getStudents() {
		return students;	
	}
	
	public boolean addStudent(StudentRole student) {
		boolean succcess = students.add(student);
		if(succcess) {
			student.addCourse(this);
		}
		return succcess;	
	}
	
	public boolean addLecturer(StaffRole lecturer) {
		boolean succcess = lecturers.add(lecturer);
		if(succcess) {
			lecturer.addCourse(this);
		}
		return succcess;	
	}
	
	@Override
	public String toString() {
		return "Course [code=" + code + ", credits=" + credits + ", courseKind=" + courseKind + "]";
	}
	
	
}

