package whiteboard.student;

import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
* This class creates attendance for students. The student will have their IDs.
* @author Mireille Mwiza Iradukunda
*
*/
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
	
	/**
	 * Superclass where it takes student's id as a parameter.
	 * @param id
	 */
	public void setStudId(int studId) {
		this.studId = studId;
	}
	/**
	 * A function to get the existing student id
	 * @return id
	 */
	public int getStudId() {
		return studId;
	}
	/**
	 * A function to set student's id.
	 * @param id
	 */
	public void setLectureId(int lectureId) {
		this.lectureId = lectureId;
	}
	/**
	 * A function to set student's id.
	 * @return lectureId
	 */
	public int getLectureId() {
		return lectureId;
	}
	
	/**
	 * Returns a string containing the student ID and Lecture ID and ID contents
	 * @return ID,StudID,lectureID
	 */
	@Override
	public String toString() {
		return this.ID+"===="+this.studId+"===="+this.lectureId;
	}
	/**
	 * This function parses the ID and studID and lectureID and puts it in the attendance string
	 * @return lectureId
	 */
	public String parseStringData(String[] attendance) {
		this.ID = Integer.parseInt(attendance[0]);
		this.studId = Integer.parseInt(attendance[1]);
		this.lectureId = Integer.parseInt(attendance[2]);
	
		return attendance[3];
	}
}