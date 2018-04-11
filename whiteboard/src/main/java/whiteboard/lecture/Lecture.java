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
/**
 * This class stores the data for lecture entity. Each and every lectures that the professor posted will be stored in the according Mysql database.
 * @author Sung Yealim
 *
 */
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
		/**
		 * Set each lecture's component
		 * @param title: lecture title
		 * @param lecDate: lecture date
		 * @param courseCode: course code of the lecture
		 * @param details: lecture details/contents
		 * @param link: lecture file link
		 * @param profId: professor enrolled in that course
		 * @param id: lecture id
		 * @param openAttendance: whether to open attendance for students or not
		 */
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
		/**
		 * Super class
		 */
		public Lecture() {
			
		}
		/**
		 * Gets the title of the lecture
		 * @return title
		 */
		public String getTitle() {
			return title;
		}
		/**
		 * Sets the title of the lecture
		 * @param title
		 */
		public void setTitle(String title) {
			this.title = title;
		}
		/**
		 * Sets the course code for the lecture
		 * @param courseCode
		 */
		public void setCourseCode(String courseCode) {
			this.courseCode = courseCode;
		}
		/**
		 * Fetches the course code of the lecture
		 * @return courseCode
		 */
		public String getCourseCode() {
			return courseCode;
		}
		/**
		 * Sets the date of the lecture
		 * @param dAte
		 */
		public void setDate(Date dAte) {
			this.lecDate = dAte;
		}
		/**
		 * Fetches the date of the lecture
		 * @return lecDate
		 */
		public Date getDate() {
			return this.lecDate;
		}
		/**
		 * Sets the details/comments of the lecture
		 * @param details
		 */
		public void setDetails(String details) {
			this.details = details;
		}
		/**
		 * Fetches the detail of the lecture
		 * @return details
		 */
		public String getDetails() {
			return details;
		}
		/**
		 * Sets the link of the file that the professor would like to post on the lecture
		 * @param link
		 */
		public void setLink(String link) {
			this.link = link;
		}
		/**
		 * Fetches the link in the lecture
		 * @return link
		 */
		public String getLink() {
			return link;
		}
		/**
		 * Sets the professor's id for the lecture
		 * @param profId
		 */
		public void setProfId(int profId) {
			this.profId = profId;
		}
		/**
		 * Fetches the professor's id for the lecture
		 * @return profId
		 */
		public int getProfId() {
			return profId;
		}
		/**
		 * Sets the lecture id
		 * @param lectureId
		 */
		public void setId(int lectureId) {
			this.lectureId = lectureId;
		}
		/**
		 * Fetches the lecture id
		 * @return lectureId
		 */
		public int getId() {
			return lectureId;
		}
		/**
		 * If attendance is true, the attendance field appears for the student in their view lecture. False otherwise.
		 * @return openAttendance
		 */
		public boolean isAttendance() {
			return openAttendance;
		}
		/**
		 * Sets the attendance value to true or false
		 * @param attendance
		 */
		public void setAttendance(boolean attendance) {
			this.openAttendance = attendance;
		}
		
//		public Set<Document> geLectureDocuments() {
//			return lectureDocuments;
//		}
		/**
		 * Converts the full information of the lecture to string value.
		 */
		@Override
		public String toString() {
			return this.title+"===="+this.lecDate+"===="+this.courseCode+"===="+this.details+
					"===="+this.link+"===="+this.profId+"===="+this.lectureId+"===="+this.openAttendance;
		}
		/**
		 * Parses and splits the data fetched
		 * @param dataSplit
		 * @return  
		 */
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
