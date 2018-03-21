package whiteboard.admin;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes
public class EnrollPerson {
	
	public String username;
	public int id;
	public boolean enrolled;
	
	public EnrollPerson() {
		
	}
	
	public EnrollPerson(int id, boolean enrolled, String username) {

		super();
		this.id = id;
		this.username = username;
		this.enrolled = enrolled;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public boolean isEnrolled() {
		return enrolled;
	}

	public void setEnrolled(boolean enrolled) {
		this.enrolled = enrolled;
	}
	
	@Override
	public String toString() {
		return ""+this.id+"="+this.enrolled+"="+this.username;
	}
}