package login;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


public interface PersonRepository extends Repository<Person, Long> {
	
	Person findById(@Param("id")Long id);
	void save(Person p);
	
	
}
