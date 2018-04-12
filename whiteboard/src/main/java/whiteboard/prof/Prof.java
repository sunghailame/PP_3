package whiteboard.prof;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.SessionAttributes;
/**
 * This class creates the entity for users who has their role as professors. The professor will have their IDs.
 * @author Sung Yealim
 *
 */
@Entity
@Table(name = "Professor")
@SessionAttributes("Professor")
public class Prof {
	
	@NotNull
	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	public int id;
	
	public Prof() {
		
	}
	/**
	 * Superclass where it takes professor's id as a parameter.
	 * @param id
	 */
	public Prof(int id) {

		super();
		this.id = id;
	}
/**
 * A function to call existing professor's id
 * @return id
 */
	public int getId() {
		return id;
	}
/**
 * A function to set professor's id.
 * @param id
 */
	public void setId(int id) {
		this.id = id;
	}

	
}