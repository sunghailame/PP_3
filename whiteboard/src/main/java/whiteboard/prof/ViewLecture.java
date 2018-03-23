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

@SessionAttributes
public class ViewLecture {

	public String title;
	public Date date;
	public String courseCode;
	public boolean view;
	public int profId;
	
	public ViewLecture(String title, Date date, String courseCode, boolean view, int profId) {
		super();
		this.title = title;
		this.date = date;
		this.courseCode = courseCode;
		this.view = view;
		this.profId = profId;
	}

	public ViewLecture() {
		
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
	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}
	
	public void setProfId(int profId) {
		this.profId = profId;
	}
	
	public int getProfId() {
		return profId;
	}


	@Override
	public String toString() {
		return "Title: "+this.title+" Date: "+this.date+" Course Code: "+this.courseCode+" View: "+
				this.view+" ProfId: "+this.profId;
	}
	
	public String toStringData() {
		return this.title+"===="+this.date+"===="+this.courseCode+"===="+this.view+"===="+this.profId;
	}
}