package whiteboard.login;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * A class that represents a "person" entity in the database
 * @author Sydney Shile Teh
 *
 */
@Entity
@Table(name = "person")
@SessionAttributes("person")
public class Person {
	
	@NotNull
	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	public int id;
	

//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name = "ID")
//	private List<Person> personList;
	
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
	
	/**
	 * Person constructor
	 * @param username
	 * @param password
	 * @param name
	 * @param email
	 * @param role
	 * @param id
	 */
	public Person(String username, String password, String name, String email, String role, int id) {

		super();
		this.role = role;
		this.username = username;
		this.password = password;
		this.id = id;
		this.email = email;
		this.name = name;
	}

	/**
	 * Get person's ID
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set person's ID
	 * @param id 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get person's name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set person's name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get person's email
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Set person's email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get person's username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set person's username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Get person's password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set persons's password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Get person's role
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Set person's role
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * Returns all values of person as string with labels
	 * @return a string
	 */
	@Override
	public String toString() {
		return "ID: "+this.id+" Name: "+this.name+" Username: "+this.username+" Email: "+this.email+" Role: "+this.role+" Password: "+this.password;
	}
	
	/**
	 * Put all data of person in a string
	 * @return a string of data
	 */
	public String toStringData() {
		return this.id+"===="+this.name+"===="+this.username+"===="+this.email+"===="+this.role+"===="+this.password;
	}
	
	/**
	 * Parse data into the class
	 * @param dataSplit
	 */
	public void parseStringData(String[] dataSplit) {
		this.id = Integer.parseInt(dataSplit[0]);
		this.name = dataSplit[1];
		this.username = dataSplit[2];
		this.email = dataSplit[3];
		this.role = dataSplit[4];
		this.password = dataSplit[5];
	}
}