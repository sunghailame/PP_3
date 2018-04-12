package whiteboard.prof;

import javax.persistence.Entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.bind.annotation.SessionAttributes;
/**
 * This class helps the student and professor to view the lecture details.
 * @author Sung Yealim
 *
 */
@SessionAttributes
public class ViewLecture {

	public String title;
	public Date date;
	public String courseCode;
	public boolean view;
	public int profId;
	public String details;
	public String link;
	public boolean attendance;
	/**
	 * Lecture details contain the following parameters.
	 * @param title: lecture title
	 * @param date: lecture date
	 * @param courseCode: course code of the lecture
	 * @param view: to decide whether to view the lecture selected or not
	 * @param profId: enrolled professor's id
	 * @param link: link of the lecture files
	 * @param details: lecture details/content
	 * @param attendance: attendance of the students who attended this lecture
	 */
	public ViewLecture(String title, Date date, String courseCode, boolean view, int profId, String link, String details, boolean attendance) {
		super();
		this.title = title;
		this.date = date;
		this.courseCode = courseCode;
		this.view = view;
		this.profId = profId;
		this.details = details;
		this.link = link;
		this.attendance = attendance;
	}
	/**
	 * Super class
	 */
	public ViewLecture() {
		
	}
	/**
	 * It fetches the lecture title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * It sets the lecture title
	 * @param title: lecture title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * It sets the course code of the lecture
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	/**
	 * It fetches the course code
	 * @return courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}
	/**
	 * It sets the date of the lecture
	 * @param date: date of the lecture
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * It fetches the date of the lecture
	 * @return date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * It decides if the user would like to view the lecture or not
	 * @return view
	 */
	public boolean isView() {
		return view;
	}
	/**
	 * It sets the boolean value to true if the user wants to view the lecture and false otherwise
	 * @param view
	 */
	public void setView(boolean view) {
		this.view = view;
	}
	/**
	 * It sets the professor's id for the according lecture for the course
	 * @param profId
	 */
	public void setProfId(int profId) {
		this.profId = profId;
	}
	/**
	 * It fetches the professor's id for the according lecture for the course
	 * @return profId
	 */
	public int getProfId() {
		return profId;
	}
	/**
	 * It sets the details of the lecture
	 * @param details: content and other comments about the lecture
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * It fetches the details of the lecture
	 * @return details
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * It sets the link of the file that the professor would like to post for the lecture
	 * @param link: link of the file or website that the professor would like to post for the lecture
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * It fetches the link of the file to the according lecture
	 * @return link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * Prompts professor to open the attendance for the students
	 * @return attendance
	 */
	public boolean isAttendance() {
		return attendance;
	}
	/**
	 * Shows attendance if isAttendance() is true
	 * @param attendance
	 */
	public void setAttendance(boolean attendance) {
		this.attendance = attendance;
	}

	/**
	 * Converts the full information of view lecture class to string
	 */
	@Override
	public String toString() {
		return this.title+"===="+this.date+"===="+this.courseCode+"===="+this.details+
				"===="+this.link+"===="+this.profId+"===="+this.attendance;
	}
	/**
	 * Parses and splits the data collected
	 * @param dataSplit
	 * @return na
	 */
	public String parseStringData(String[] dataSplit) {
		this.title = dataSplit[0];
		this.date = Date.valueOf(dataSplit[1]);
		this.courseCode = dataSplit[2];
		this.details =dataSplit[3];
		this.link = dataSplit[4];
		this.profId = Integer.parseInt(dataSplit[5]);
		this.attendance = Boolean.getBoolean(dataSplit[6]);
		
		if(dataSplit.length <  8) {
			return dataSplit[7];
		} else {
			return "na";
		}
	}
}