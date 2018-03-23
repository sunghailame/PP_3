package whiteboard.prof;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes
public class FormWrapper{
	
	public ArrayList<ViewLecture> users;
	
	public ArrayList<ViewLecture> getUsers(){
		return users;
	}
	
	public void setUsers(ArrayList<ViewLecture> users) {
		this.users = users;
	}
	
}