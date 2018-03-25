package whiteboard.prof;



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
	public class CheckAttendance {

		@NotNull
		@Column(name = "ID")
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		public int ID;
		@Column(name = "CourseCode")
		public String CourseCode;
		@Column(name = "SectionNo")
		public String SectionNo;
		@Column(name = "Time")
		public Time time;
		@Column(name = "Date")
		public Date date;
		@Column(name = "Present")
		public Boolean present;
		
	public CheckAttendance(int ID, String CourseCode, String SectionNo, Time time, Date date) {
		super();
		this.ID = ID;
		this.CourseCode = CourseCode;
		this.SectionNo = SectionNo;
		this.time = time;
		this.date = date;
	}
	public CheckAttendance() {
		
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

	public void setTime(Time time) {
		this.time = time;
	}

	public Time getTime() {
		return time;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "ID: "+this.ID+"Course Code: "+this.CourseCode+"Section Number: "+this.SectionNo+"Time: "+this.time+"Date: "+this.date;
	}
	
}
