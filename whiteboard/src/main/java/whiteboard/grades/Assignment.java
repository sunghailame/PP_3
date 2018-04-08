package whiteboard.grades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	
	public Assignment(int assId, String courseCode, String assName, int percentage) {
		super();
		this.assId = assId;
		this.courseCode = courseCode;
		this.percentage = percentage;
		this.assName = assName;
	}
	
	public Assignment() {
		
	}
	
	public int getAssId() {
		return assId;
	}
	
	public void setAssName(String assName) {
		this.assName = assName;
	}
	
	public String getAssName() {
		return assName;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	public int getPercentage() {
		return percentage;
	}
	
	@Override
	public String toString() {
		return this.assId+"===="+this.assName+"===="+this.courseCode+"===="+this.percentage;
	}
}
