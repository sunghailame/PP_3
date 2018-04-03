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
	public String courseCode;
	@Column(name = "CourseName")
	public String course_name;
	@Column(name = "BuildingId")
	public int buildingId;
	
	public Course(String course_code, String course_name, int buildingId) {
		super();
		this.courseCode = course_code;
		this.course_name = course_name;
		this.buildingId = buildingId;
	}

	public Course() {
		
	}
	
	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public String getCourse_code() {
		return courseCode;
	}

	public void setCourse_code(String course_code) {
		this.courseCode = course_code;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}


	@Override
	public String toString() {
		return " Course Code: "+this.courseCode+" Course Name: "+this.course_name;
	}
	
	public String toStringData() {
		return this.courseCode+"===="+this.course_name+"===="+this.buildingId;
	}
}



