package login;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends Repository<Person, Long> {
//	@Query("SELECT person FROM Person person left join fetch person.username WHERE person.id =:id")
	Person findById(@Param("id")int id);
	void save(Person p);
	
	
}
