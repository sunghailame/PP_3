package whiteboard.admin;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.SessionAttributes;
@SessionAttributes
public class FormWrapper{
	
	public ArrayList<DummyStudent> users;
	
	public ArrayList<DummyStudent> getUsers(){
		return users;
	}
	
	public void setUsers(ArrayList<DummyStudent> users) {
		this.users = users;
	}
	
}