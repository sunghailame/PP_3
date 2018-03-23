package whiteboard.login;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.bind.annotation.SessionAttributes;

@Entity
@Table(name = "person")
@SessionAttributes("person")
public class Person {
	
	@NotNull
	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	public int id;
	

	@Column(name = "Name")
	public String name;

	@Column(name = "Email")
	@Email(message = "*Please provide a valid Email")
	public String email;

	@Column(name = "username")
	public String username;

	@Column(name = "Password")
	public String password;

	@Column(name = "Role")
	public String role;
	
	public Person() {
		
	}
	
	public Person(String username, String password, String name, String email, String role, int id) {

		super();
		this.role = role;
		this.username = username;
		this.password = password;
		this.id = id;
		this.email = email;
		this.name = name;
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
	
	@Override
	public String toString() {
		return "ID: "+this.id+" Name: "+this.name+" Username: "+this.username+" Email: "+this.email+" Role: "+this.role+" Password: "+this.password;
	}
	
	public String toStringData() {
		return this.id+"===="+this.name+"===="+this.username+"===="+this.email+"===="+this.role+"===="+this.password;
	}
	
	public void parseStringData(String[] dataSplit) {
		this.id = Integer.parseInt(dataSplit[0]);
		this.name = dataSplit[1];
		this.username = dataSplit[2];
		this.email = dataSplit[3];
		this.role = dataSplit[4];
		this.password = dataSplit[5];
	}
}