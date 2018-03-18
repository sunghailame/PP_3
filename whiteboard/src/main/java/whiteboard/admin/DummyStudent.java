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

public class DummyStudent {
	
	public int id;
	

	public String name;

	public String email;

	public String username;

	public String password;

	public String role;
	
	public boolean enrolled;
	
	
	public DummyStudent() {
		
	}
	
	public DummyStudent(String username, String password, String name, String email, String role, int id, boolean enrolled) {

		super();
		this.role = role;
		this.username = username;
		this.password = password;
		this.id = id;
		this.email = email;
		this.name = name;
		this.enrolled = enrolled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean isEnrolled() {
		return enrolled;
	}

	public void setEnrolled(boolean enrolled) {
		this.enrolled = enrolled;
	}
	
	@Override
	public String toString() {
		return "ID: "+this.id+" Name: "+this.name+" Username: "+this.username+" Email: "+this.email+" Role: "+this.role+" Password: "+this.password;
	}
	
	public String toStringData() {
		return this.id+"="+this.name+"="+this.username+"="+this.email+"="+this.role+"="+this.password;
	}
}