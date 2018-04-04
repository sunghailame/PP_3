package whiteboard.grades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

//Commented because of entityManager exceptions
@Entity
@Table(name="Grades")
public class Grades {

	@Id
	@NotNull
	@Column(name="gradeId")
	public int gradeId;

	@Column(name = "CourseCode")
	public String courseCode;
	@Column(name = "Assignments")
	public int assignments;
	@Column(name = "Attendance")
	public int attendance;
	@Column(name = "Exams")
	public int exams;
	
	public Grades(String courseCode, int assignments, int attendance, int exams) {
		super();
		this.courseCode = courseCode;
		this.assignments = assignments;
		this.attendance = attendance;
		this.exams = exams;
	}
	
	public Grades() {
		
	}
	
	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	
	public void setAssignments(int assignments) {
		this.assignments = assignments;
	}
	
	public int getAssignments() {
		return assignments;
	}
	
	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}
	
	public int getAttendance() {
		return attendance;
	}
	
	public void setExams(int exams) {
		this.exams = exams;
	}
	
	public int getExams() {
		return exams;
	}
	
	@Override
	public String toString() {
		return this.courseCode+"===="+this.assignments+"===="+this.attendance+"===="+this.exams;
	}
}
