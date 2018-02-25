package whiteboard.admin;

import whiteboard.login.Person;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Admin")
public class Admin {

	@ManyToOne
	@JoinColumn(name = "ID")
	private Person person;
	
	
	
}
