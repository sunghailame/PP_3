package whiteboard.student;

import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Attendance")
@SessionAttributes
public class Attendance {

	@NotNull
	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int ID;
	@Column(name = "CourseCode")
	public String CourseCode;
	@Column(name = "SectionNo")
	public String SectionNo;

	@Column(name = "Date")
	public Date date;
	@Column(name = "LectureTitle")
	public String lecture;
	@Column(name = "ProfId")
	public int profId;
	@Column(name = "StudId")
	public int studId;
	
	public Attendance(int ID, String CourseCode, String SectionNo, Date date, int profId, int studId, String lecture) {
		super();
		this.ID = ID;
		this.CourseCode = CourseCode;
		this.SectionNo = SectionNo;
		this.date = date;
		this.profId = profId;
		this.studId = studId;
		this.lecture = lecture;
	}
	public Attendance() {
		
	}
	
	public int getID() {
		return ID;
	}
	
	public void setCourseCode(String CourseCode) {
		this.CourseCode = CourseCode;
	}
	
	public String getCourseCode() {
		return CourseCode;
	}
	
	public void setSection(String SectionNo) {
		this.SectionNo = SectionNo;
	}
	
	public String getSection() {
		return SectionNo;
	}
	
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setLecture(String lecture) {
		this.lecture = lecture;
	}
	
	public String getLecture() {
		return lecture;
	}
	
	public void setProfId(int profId) {
		this.profId = profId;
	}
	
	public int getProfId() {
		return profId;
	}
	
	public void setStudId(int studId) {
		this.studId = studId;
	}
	
	public int getStudId() {
		return studId;
	}
	
	
	
	
	
	@Override
	public String toString() {
		return this.ID+"===="+this.CourseCode+"===="+this.SectionNo+"===="+this.lecture+"===="+this.date+"===="+this.profId+"===="+this.studId;
	}
	
	public String parseStringData(String[] attendance) {
		this.ID = Integer.parseInt(attendance[0]);
		this.CourseCode = attendance[1];
		this.SectionNo = attendance[2];
		this.lecture =attendance[3];
		this.date = Date.valueOf(attendance[4]);
		this.profId = Integer.parseInt(attendance[5]);
		this.studId = Integer.parseInt(attendance[6]);
		
		return attendance[7];
	}
}