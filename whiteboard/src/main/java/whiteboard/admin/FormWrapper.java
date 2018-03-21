package whiteboard.admin;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes
public class FormWrapper{
	
	public ArrayList<EnrollPerson> users;
	
	public ArrayList<EnrollCourse> courses;
	
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
	
}