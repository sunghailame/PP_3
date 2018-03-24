package whiteboard.lecture;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Lecture")
public class Lecture {
	
	@NotNull
	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	
		@Column(name = "Title")
		public String title;
		@Column(name = "Date")
		public Date date;
		@Column(name = "CourseCode")
		public String courseCode;
		@Column(name = "Details")
		public String details;
		@Column(name = "Link")
		public String link;
		@Column(name = "ProfId")
		public int profId;
		@Column(name = "Attendance")
		public boolean attendance;
		
		public Lecture(String title, Date date, String courseCode, String details, String link, int profId, int id, boolean attendance) {
			super();
			this.title = title;
			this.date = date;
			this.courseCode = courseCode;
			this.details = details;
			this.link = link;
			this.profId = profId;
			this.id = id;
			this.attendance = attendance;
		}

		public Lecture() {
			
		}
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}

		public void setCourseCode(String courseCode) {
			this.courseCode = courseCode;
		}

		public String getCourseCode() {
			return courseCode;
		}
		
		public void setDate(Date date) {
			this.date = date;
		}

		public Date getDate() {
			return date;
		}

		public void setDetails(String details) {
			this.details = details;
		}

		public String getDetails() {
			return details;
		}
		
		public void setLink(String link) {
			this.link = link;
		}

		public String getLink() {
			return link;
		}
		
		public void setProfId(int profId) {
			this.profId = profId;
		}
		
		public int getProfId() {
			return profId;
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public boolean isAttendance() {
			return attendance;
		}

		public void setAttendance(boolean attendance) {
			this.attendance = attendance;
		}

		@Override
		public String toString() {
			return this.title+"===="+this.date+"===="+this.courseCode+"===="+this.details+
					"===="+this.link+"===="+this.profId+"===="+this.attendance;
		}
		
		public String parseStringData(String[] dataSplit) {
			this.title = dataSplit[0];
			this.date = Date.valueOf(dataSplit[1]);
			this.courseCode = dataSplit[2];
			this.details =dataSplit[3];
			this.link = dataSplit[4];
			this.profId = Integer.parseInt(dataSplit[5]);
			this.attendance = Boolean.getBoolean(dataSplit[6]);
			return dataSplit[7];
		}
	}
