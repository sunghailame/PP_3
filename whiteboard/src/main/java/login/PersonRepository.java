package login;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends Repository<Person, Long> {
	
	Person findById(@Param("id")int id);
	void save(Person p);
}
