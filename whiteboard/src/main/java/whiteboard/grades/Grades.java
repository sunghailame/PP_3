package whiteboard.grades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;

/**
 * This class contains information of grades entity that the professor sets for each assignment
 * @author Sung Yealim
 *
 */
@Entity
@Table(name="Grades")
public class Grades {

	@NotNull
	@Column(name = "GradeId")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int gradeId;
	
	@Column(name = "StudentId")
	public int studentId;
	@Column(name = "Grade")
	public int grade;
	@Column(name = "AssId")
	public int assId;
	/**
	 * Grade information includes grade id, student id, grade, and assignment id
	 * @param gradeId: unique grade id
	 * @param studentId: student's id of who will be graded
	 * @param grade: grade of the assignment
	 * @param assId: assignment id to be graded
	 */
	public Grades(int gradeId, int studentId, int grade, int assId) {

		super();
		this.gradeId = gradeId;
		this.studentId = studentId;
		this.grade = grade;
		this.assId = assId;
	}
	/**
	 * Superclass of the grade class
	 */
	public Grades() {
		
	}
	
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	/**
	 * Fetches student's id who's assignment will be graded
	 * @return studentId
	 */
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	/**
	 * Fetches grade id
	 * @return gradeId
	 */
	public int getGradeId() {
		return gradeId;
	}
	/**
	 * Sets grade id
	 * @param gradeId
	 */
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	/**
	 * Sets grade for the according assignmnet
	 * @param grade
	 */

	/**
	 * Fetches assignment id
	 * @return assId
	 */
	public int getAssId() {
		return assId;
	}
	/**
	 * Converts the information of grade into string value
	 */
	@Override
	public String toString() {
		return this.gradeId+"===="+this.studentId+"===="+this.grade+"===="+this.assId;
	}
	
	public void parseString(String stuff) {
		String[] parse = stuff.split("====");
		this.gradeId = Integer.parseInt(parse[0]);
		this.studentId = Integer.parseInt(parse[1]);
		this.grade = Integer.parseInt(parse[2]);
		this.assId = Integer.parseInt(parse[3]);
	}
}
