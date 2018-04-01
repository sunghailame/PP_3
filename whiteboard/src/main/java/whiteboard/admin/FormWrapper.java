package whiteboard.admin;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.SessionAttributes;

import whiteboard.lecture.Lecture;
import whiteboard.prof.ViewLecture;

@SessionAttributes
public class FormWrapper{
	
	public ArrayList<EnrollPerson> users;
	
	public ArrayList<EnrollCourse> courses;
	
	public ArrayList<Lecture> lectures;
	
	public ArrayList<Lecture> lecture;
	
	public ArrayList<EnrollPerson> getUsers(){
		return users;
	}
	
	public void setUsers(ArrayList<EnrollPerson> users) {
		this.users = users;
	}
	
	public ArrayList<EnrollCourse> getCourses(){
		return courses;
	}
	
	public void setCourses(ArrayList<EnrollCourse> courses) {
		this.courses = courses;
	}
	
	public ArrayList<Lecture> getLectures(){
		return lectures;
	}
	
	public void setLectures(ArrayList<Lecture> lectures) {
		this.lectures = lectures;
	}
	
	public ArrayList<Lecture> getLecture(){
		return lecture;
	}
	
	public void setLecture(ArrayList<Lecture> lecture) {
		this.lecture = lecture;
	}
	
}