package whiteboard.course;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.bind.annotation.SessionAttributes;

@Entity
@Table(name = "Course")
public class Course {
	
	@NotNull
	@Id
	@Column(name = "CourseCode")
	public String course_code;
	
	@Column(name = "CourseName")
	public String course_name;


	
	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public Course(String course_code, String course_name) {
		super();
		this.course_code = course_code;
		this.course_name = course_name;
	}

	public Course() {
		
	}

	@Override
	public String toString() {
		return " Course Code: "+this.course_code+" Course Name: "+this.course_name;
	}
	
	public String toStringData() {
		return this.course_code+"="+this.course_name;
	}
}


