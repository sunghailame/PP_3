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
		
		public Lecture(String title, Date date, String courseCode) {
			super();
			this.title = title;
			this.date = date;
			this.courseCode = courseCode;
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


		@Override
		public String toString() {
			return "Title: "+this.title+" Date: "+this.date+" Course Code: "+this.courseCode;
		}
		
		public String toStringData() {
			return this.title+"="+this.date+"="+this.courseCode;
		}
	}
