package login;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends Repository<Person, Long> {
	
	Person findByID(@Param("ID") Integer id);
	
	void save(Person p);
	
	
}
