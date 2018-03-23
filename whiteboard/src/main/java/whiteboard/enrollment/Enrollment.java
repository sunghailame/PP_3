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
	public int person_id;
	

	@Column(name = "CourseCode")
	public String course_code;

	@Column(name = "SectionNo")
	public String section_no;
	
	@Column(name = "Role")
	public String role;

	
	public Enrollment(int id, int person_id, String course_code, String section_no, String role) {
		super();
		this.id = id;
		this.person_id = person_id;
		this.course_code = course_code;
		this.section_no = section_no;
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
		return person_id;
	}

	public void setPersonId(int person_id) {
		this.person_id = person_id;
	}
	
	

	public String getCourseCode() {
		return course_code;
	}

	public void setCourseCode(String CourseCode) {
		this.course_code = CourseCode;
	}

	public String getSectionNo() {
		return section_no;
	}

	public void setSectionNo(String SectionNo) {
		this.section_no = SectionNo;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return this.person_id+"===="+this.course_code+"===="+this.section_no+"===="+this.role;
	}
	
	public String toStringData() {
		return this.person_id+"===="+this.course_code+"===="+this.section_no+"===="+this.role;
	}
	
	public void parseStringData(String[] dataSplit) {
		this.person_id = Integer.parseInt(dataSplit[0]);
		this.course_code = dataSplit[1];
		this.section_no = dataSplit[2];
		this.role = dataSplit[3];
		this.id = 0;
	}
}