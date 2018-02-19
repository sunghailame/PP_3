package login;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


public interface PersonRepository extends Repository<Person, Long> {
	
	Collection<Person> findById(@Param("id")Long id);
	void save(Person Person);
}
