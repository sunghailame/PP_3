package whiteboard.grades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;


@Entity
@Table(name="Grades")
public class Grades {

	@NotNull
	@Column(name = "GradeId")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int gradeId;
	
	@Column(name = "StudentId")
	public String studentId;
	@Column(name = "Grade")
	public int grade;
	@Column(name = "ID")
	public int assId;
	
	public Grades(int gradeId, String studentId, int grade, int assId) {

		super();
		this.gradeId = gradeId;
		this.studentId = studentId;
		this.grade = grade;
		this.assId = assId;
	}
	
	public Grades() {
		
	}
	

	public String getStudentId() {
		return studentId;
	}
	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public int getAssId(int assId) {
		return assId;
	}
	
	@Override
	public String toString() {
		return this.studentId+"===="+this.grade+"===="+this.assId;
	}
}
