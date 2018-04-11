package whiteboard.grades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 * This class contains information of assignment entity created by professor for each course.
 * @author Sung Yealim
 *
 */
@Entity
@Table(name="Assignment")
public class Assignment {

	@NotNull
	@Column(name = "AssID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int assId;
	
	@Column(name = "CourseCode")
	public String courseCode;
	@Column(name = "AssignmentName")
	public String assName;
	@Column(name = "Percentage")
	public int percentage;
	/**
	 * Contains information about the assingment such as assignment id, course code, assignment name, and percentage of each assignment.
	 * @param assId: unique assignment id
	 * @param courseCode: course code of this assignment
	 * @param assName: assignment name
	 * @param percentage: percentage weighted
	 */
	public Assignment(int assId, String courseCode, String assName, int percentage) {
		super();
		this.assId = assId;
		this.courseCode = courseCode;
		this.percentage = percentage;
		this.assName = assName;
	}
	/**
	 * Superclass for assignment class
	 */
	public Assignment() {
		
	}
	/**
	 * Fetches assignment id which is unique
	 * @return assId
	 */
	public int getAssId() {
		return assId;
	}
	/**
	 * Sets assignment id for each assignment name
	 * @param assName: assignment name
	 */
	public void setAssName(String assName) {
		this.assName = assName;
	}
	/**
	 * Fetches assignment name
	 * @return assName
	 */
	public String getAssName() {
		return assName;
	}
	/**
	 * Fetches the course code for that assignment
	 * @return courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}
	/**
	 * Sets percentage for that assignment
	 * @param percentage
	 */
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	/**
	 * Fetches the percentage for that assignment
	 * @return percentage
	 */
	public int getPercentage() {
		return percentage;
	}
	/**
	 * Converts the information of the assignment into string value.
	 */
	@Override
	public String toString() {
		return this.assId+"===="+this.assName+"===="+this.courseCode+"===="+this.percentage;
	}
}
