package whiteboard.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import whiteboard.login.Person;

public interface StudentRepository extends Repository<Person, Long> {

	Person findByUsername(String username);
	
	void save(Person p);
	
	
}
