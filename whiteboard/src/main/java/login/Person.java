package login;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "person")

public class Person {

	@NotEmpty
	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public String id;
	
	@Column(name = "Name")
	public String name;
	@Column(name = "Email")
	public String email;
	@Column(name = "username")
	public String username;
	@Column(name = "Password")
	public String password;
	@Column(name = "Role")
	public String role;
	
	
	public Person() {
		
	}
	
	public Person(String username, String password, String name, String email, String role, Integer id) {

		super();
		this.role = role;
		this.username = username;
		this.password = password;
		this.id = id;
		this.email = email;
		this.name = name;
	}

//	public String getId() {
//		return id;
//	}
//
	public void setId(String id) {
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
	
}