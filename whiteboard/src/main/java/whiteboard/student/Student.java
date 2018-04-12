package whiteboard.student;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.SessionAttributes;
/**
* This class creates the entity for users who has their role as students. The student will have their IDs.
* @author Mireille Mwiza Iradukunda
*
*/
@Entity
@Table(name = "Student")
@SessionAttributes("Studednt")
public class Student {
	
	@NotNull
	@Column(name = "StudentID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	public int id;
	
	public Student() {
		
	}
	/**
	 * Superclass where it takes student's id as a parameter.
	 * @param id
	 */
	public Student(int id) {

		super();
		this.id = id;
	}
	/**
	 * A function to get the existing student id
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * A function to set student's id.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	
}