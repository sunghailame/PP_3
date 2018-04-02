package whiteboard.login;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import whiteboard.admin.Admin;
import whiteboard.prof.Prof;
import whiteboard.student.Student;

//import whiteboard.enrollment.Enrollment;


public interface PersonRepository extends Repository<Person, Long> {
//	@Query("SELECT person FROM Person person left join fetch person.username WHERE person.id =:id")
//	Person findById(@Param("id")int id);
	Person findByUsername(String username);
	Person findByRole(String role);
	
	void save(Person p);
	Person findById(int studId);
	ArrayList<Person> findAll();

}
