package whiteboard.notification;

import javax.persistence.Entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.SessionAttributes;


@Entity
@Table(name = "Notification")
@SessionAttributes("Notification")
public class Notification {

	@NotNull
	@Column(name = "NoteId")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int noteId;
	
	@Column(name = "PersonId")
	public int personId;

	@Column(name = "CourseCode")
	public String courseCode;

	@Column(name = "Note")
	public byte[] note;

	@Column(name = "StartDate")
	public Date startDate;
	
	@Column(name = "EndDate")
	public Date endDate;

	public Notification(int personId, String courseCode, byte[] note, Date startDate, Date endDate) {
		super();
		this.personId = personId;
		this.courseCode = courseCode;
		this.note = note;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Notification() {
		
	}
	
	public int getPersonId() {
		return personId;
	}
	
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	public String getCourseCode() {
		return courseCode; 
	}
	
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public byte[] getNote() {
		return this.note;
	}
	
	public void setNote(byte[] note) {
		this.note = note;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setENdDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
