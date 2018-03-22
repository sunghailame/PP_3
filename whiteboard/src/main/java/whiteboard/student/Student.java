package whiteboard.student;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.SessionAttributes;

@Entity
@Table(name = "Student")
@SessionAttributes("Studednt")
public class Student {
	
	@NotNull
	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	public int id;
	
	public Student() {
		
	}
	
	public Student(int id) {

		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}