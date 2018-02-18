package login;

import org.springframework.data.repository.Repository;

public interface PersonRepository extends Repository<Person, Long> {
	
	void save(Person p);
	
	
}
