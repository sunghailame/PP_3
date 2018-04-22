package whiteboard.admin;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.SessionAttributes;

import whiteboard.grades.Assignment;
import whiteboard.grades.Grades;
import whiteboard.lecture.Lecture;

@SessionAttributes
public class FormWrapper{
	
	public ArrayList<EnrollPerson> users;
	
	public ArrayList<EnrollCourse> courses;
	
	public ArrayList<Lecture> lectures;
	
	public ArrayList<Lecture> lecture;
	
	public ArrayList<Assignment> assignment;
	
	public ArrayList<Assignment> assignments;
	
	public ArrayList<Grades> grade;
	
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
	
	public ArrayList<Assignment> getAssignment(){
		return assignment;
	}
	public void setAssignment(ArrayList<Assignment> assignment) {
		this.assignment = assignment;
	}
	public ArrayList<Assignment> getAssignments(){
		return assignments;
	}
	public void setAssignments(ArrayList<Assignment> assignments) {
		this.assignments = assignments;
	}
	
	public ArrayList<Grades> getStud() {
		return grade;
	}
	
	public void setGrade(ArrayList<Grades> grade) {
		this.grade = grade;
	}
}