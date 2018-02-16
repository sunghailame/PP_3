package login;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Person")

public class User {

	@NotEmpty
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;
	public String name;
	public String email;
	public String username;
	public String password;
	public String cpassword;

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public User() {
		
	}
	
	public User(String username, String password, String name, Integer id, String email, String cpassword) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
		this.email = email;
		this.name = name;
		this.cpassword = cpassword;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

}