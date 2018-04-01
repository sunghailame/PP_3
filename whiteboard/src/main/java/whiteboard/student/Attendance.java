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
public class Attendance {

	@NotNull
	@Column(name = "ID")
	@Id
	public int ID;
	
	@Column(name = "LectureID")
	public int lectureId;
	
	@Column(name = "StuId")
	public int studId;
	
	public Attendance(int ID, int studId, int lectureId) {
		super();
		this.ID = ID;
		this.studId = studId;
		this.lectureId = lectureId;
	}
	public Attendance() {
		
	}
	
	public int getID() {
		return ID;
	}
	
	
	public void setStudId(int studId) {
		this.studId = studId;
	}
	
	public int getStudId() {
		return studId;
	}
	
	public void setLectureId(int lectureId) {
		this.lectureId = lectureId;
	}
	
	public int getLectureId() {
		return lectureId;
	}
	
	
	@Override
	public String toString() {
		return this.ID+"===="+this.studId+"===="+this.lectureId;
	}
	
	public String parseStringData(String[] attendance) {
		this.ID = Integer.parseInt(attendance[0]);
		this.studId = Integer.parseInt(attendance[1]);
		this.lectureId = Integer.parseInt(attendance[2]);
	
		return attendance[3];
	}
}