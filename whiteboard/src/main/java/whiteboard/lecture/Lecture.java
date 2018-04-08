package whiteboard.lecture;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

import whiteboard.document.Document;

@Entity
@Table(name="Lecture")
public class Lecture {
	
	@NotNull
	@Column(name = "LectureID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int lectureId;
	
		@Column(name = "Title")
		public String title;
		@Column(name = "lecDate")
		public Date lecDate;
		@Column(name = "CourseCode")
		public String courseCode;
		@Column(name = "Details")
		public String details;
		@Column(name = "Link")
		public String link;
		@Column(name = "ProfId")
		public int profId;
		@Column(name = "OpenAttendance")
		public boolean openAttendance;
		
	//	@OneToMany(mappedBy= "lecture", cascade = CascadeType.ALL)
	//	private Set<Document> lectureDocuments = new HashSet<Document>();
		
		public Lecture(String title, Date lecDate, String courseCode, String details, String link, int profId, int id, boolean openAttendance) {
			super();
			this.title = title;
			this.lecDate = lecDate;
			this.courseCode = courseCode;
			this.details = details;
			this.link = link;
			this.profId = profId;
			this.lectureId = id;
			this.openAttendance = openAttendance;
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
		
		public void setDate(Date dAte) {
			this.lecDate = dAte;
		}

		public Date getDate() {
			return this.lecDate;
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
		
		public void setId(int lectureId) {
			this.lectureId = lectureId;
		}
		
		public int getId() {
			return lectureId;
		}
		
		public boolean isAttendance() {
			return openAttendance;
		}

		public void setAttendance(boolean attendance) {
			this.openAttendance = attendance;
		}
		
//		public Set<Document> geLectureDocuments() {
//			return lectureDocuments;
//		}

		@Override
		public String toString() {
			return this.title+"===="+this.lecDate+"===="+this.courseCode+"===="+this.details+
					"===="+this.link+"===="+this.profId+"===="+this.lectureId+"===="+this.openAttendance;
		}
		
		public String parseStringData(String[] dataSplit) {
			this.title = dataSplit[0];
			this.lecDate = Date.valueOf(dataSplit[1]);
			this.courseCode = dataSplit[2];
			this.details =dataSplit[3];
			this.link = dataSplit[4];
			this.profId = Integer.parseInt(dataSplit[5]);
			this.lectureId = Integer.parseInt(dataSplit[6]);
			if(dataSplit[8].contains("attendance")) {
				this.openAttendance = true;
			}else {
				this.openAttendance = false;
			}
			return "";
		}
	}
