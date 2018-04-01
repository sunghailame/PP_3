package whiteboard.enrollment;

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
@Table(name = "Enrollment")

public class Enrollment {
	
	
	@NotNull
	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	@NotNull
	@Column(name = "PersonID") 
	public int personId;
	@Column(name = "CourseCode")
	public String courseCode;
	@Column(name = "SectionNo")
	public String sectionNo;
	@Column(name = "Role")
	public String role;

	
	public Enrollment(int id, int person_id, String course_code, String section_no, String role) {
		super();
		this.id = id;
		this.personId = person_id;
		this.courseCode = course_code;
		this.sectionNo = section_no;
		this.role = role;
	}
	
	public Enrollment() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int person_id) {
		this.personId = person_id;
	}
	
	

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String CourseCode) {
		this.courseCode = CourseCode;
	}

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String SectionNo) {
		this.sectionNo = SectionNo;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return this.personId+"===="+this.courseCode+"===="+this.sectionNo+"===="+this.role;
	}
	
	public String toStringData() {
		return this.personId+"===="+this.courseCode+"===="+this.sectionNo+"===="+this.role;
	}
	
	public void parseStringData(String[] dataSplit) {
		this.personId = Integer.parseInt(dataSplit[0]);
		this.courseCode = dataSplit[1];
		this.sectionNo = dataSplit[2];
		this.role = dataSplit[3];
		this.id = 0;
	}
}