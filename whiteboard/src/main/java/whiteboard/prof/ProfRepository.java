package whiteboard.prof;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import whiteboard.login.Person;

/**
 * This repository uses Person and Long type.
 * @author Sung Yealim
 *
 */
public interface ProfRepository extends Repository<Person, Long> {
	
	
}
