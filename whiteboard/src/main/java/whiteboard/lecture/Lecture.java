package whiteboard.lecture;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name="Lectures")
public class Lecture {
	
		
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
		
		public Lecture(String title, Date date, String courseCode, String details, String link) {
			super();
			this.title = title;
			this.date = date;
			this.courseCode = courseCode;
			this.details = details;
			this.link = link;
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

		@Override
		public String toString() {
			return "Title: "+this.title+" Date: "+this.date+" Course Code: "+this.courseCode+" Details: "
					+this.details+" Link: "+this.link;
		}
		
		public String toStringData() {
			return this.title+"%=%=%="+this.date+"%=%=%="+this.courseCode+"%=%=%="+this.details+"%=%=%="+this.link;
		}
	}
