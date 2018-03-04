package whiteboard.login;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import whiteboard.enrollment.Enrollment;;

public interface PersonRepository extends Repository<Person, Long> {
//	@Query("SELECT person FROM Person person left join fetch person.username WHERE person.id =:id")
//	Person findById(@Param("id")int id);
	Person findByUsername(String username);
	
	void save(Person p);
	
	
}
