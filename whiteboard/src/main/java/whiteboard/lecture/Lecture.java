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
		
		public Lecture(String title, Date date, String courseCode, String details, String link, int profId, int id) {
			super();
			this.title = title;
			this.date = date;
			this.courseCode = courseCode;
			this.details = details;
			this.link = link;
			this.profId = profId;
			this.id = id;
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

		@Override
		public String toString() {
			return "Title: "+this.title+" Date: "+this.date+" Course Code: "+this.courseCode+" Details: "
					+this.details+" Link: "+this.link+" ProfId: "+this.profId;
		}
		
		public String toStringData() {
			return this.title+"===="+this.date+"===="+this.courseCode+"===="+this.details+
					"===="+this.link+"===="+this.profId;
		}
	}
