package whiteboard.admin;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.SessionAttributes;

import whiteboard.prof.ViewLecture;

@SessionAttributes
public class FormWrapper{
	
	public ArrayList<EnrollPerson> users;
	
	public ArrayList<EnrollCourse> courses;
	
	public ArrayList<ViewLecture> lectures;
	
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
	
	public ArrayList<ViewLecture> getLectures(){
		return lectures;
	}
	
	public void setLectures(ArrayList<ViewLecture> lectures) {
		this.lectures = lectures;
	}
	
}