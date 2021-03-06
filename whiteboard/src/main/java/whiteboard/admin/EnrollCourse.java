package whiteboard.admin;

import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes
public class EnrollCourse {
	
	public String courseCode;
	public String courseName;
	public int buildingId;
	public boolean enrolled;
	
	public EnrollCourse() {
		
	}

	public EnrollCourse(String courseCode, String courseName, boolean enrolled, int buildingId) {

		super();
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.enrolled = enrolled;
		this.buildingId = buildingId;
	}
	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
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
		return ""+this.courseCode+"===="+this.courseCode+"===="+this.enrolled+"===="+this.buildingId;
	}
}