package whiteboard.student;

import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
* This class views attendance for students. Using Student's ID and Name
* @author Mireille Mwiza Iradukunda
*
*/
public class ViewAttendance {

	public int studId;
	public String studName;
	
	public ViewAttendance(int studId, String studName) {
		super();
		this.studId = studId;
		this.studName = studName;
	}
	
}