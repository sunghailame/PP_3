package whiteboard.prof;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes
public class FormWrapper{
	
	public ArrayList<ViewLecture> lectures;
	
	public ArrayList<ViewLecture> getLectures(){
		return lectures;
	}
	
	public void setLectures(ArrayList<ViewLecture> lectures) {
		this.lectures = lectures;
	}
	
}