//package whiteboard.course;
//
//import javax.persistence.Entity;
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//import org.hibernate.validator.constraints.Email;
//import org.springframework.web.bind.annotation.SessionAttributes;
//
//@Entity
//@Table(name = "Course")
//
//public class Course {
//	
//	@NotNull
//	@Column(name = "CourseCode")
//	public String course_code;
//	
//	@Column(name = "CourseName")
//	public String course_name;
//
//
//	
//	public Course(String CourseCode, String CourseName) {
//		super();
//		this.course_code = CourseCode;
//		this.course_name = CourseName;
//	}
//
//	public String geCourseCode() {
//		return course_code;
//	}
//
//	public void setCourseCode(String CourseCode) {
//		this.course_code = CourseCode;
//	}
//
//	public String getCourseName() {
//		return course_name;
//	}
//
//	public void setCourseNmae(String CourseName) {
//		this.course_name = CourseName;
//	}
//	
//	@Override
//	public String toString() {
//		return " Course Code: "+this.course_code+" Course Name: "+this.course_name;
//	}
//	
//	public String toStringData() {
//		return this.course_code+"="+this.course_name;
//	}
//}
//
//
