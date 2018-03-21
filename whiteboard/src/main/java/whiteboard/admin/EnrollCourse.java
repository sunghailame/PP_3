package whiteboard.admin;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes
public class EnrollCourse {
	
	public String courseCode;
	public String courseName;
	public boolean enrolled;
	
	public EnrollCourse() {
		
	}
	
	public EnrollCourse(String courseCode, String courseName, boolean enrolled) {

		super();
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.enrolled = enrolled;
	}
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String coursename) {
		this.courseName = coursename;
	}
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String coursecode) {
		this.courseCode = coursecode;
	}
	
	public boolean isEnrolled() {
		return enrolled;
	}

	public void setEnrolled(boolean enrolled) {
		this.enrolled = enrolled;
	}
	
	@Override
	public String toString() {
		return ""+this.courseCode+"="+this.courseCode+"="+this.enrolled;
	}
}