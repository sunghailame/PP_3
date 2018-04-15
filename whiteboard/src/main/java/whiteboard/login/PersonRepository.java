package whiteboard.login;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import whiteboard.admin.Admin;
import whiteboard.prof.Prof;
import whiteboard.student.Student;

//import whiteboard.enrollment.Enrollment;

/**
 * Repository interface that interacts with "person" table in database. 
 * @author SydneyTeh
 *
 */
public interface PersonRepository extends Repository<Person, Long> {
//	@Query("SELECT person FROM Person person left join fetch person.username WHERE person.id =:id")
//	Person findById(@Param("id")int id);
	/**
	 * find person with username
	 * @param username
	 * @return Person object
	 */
	Person findByUsername(String username);
	
	/**
	 * Get a list of person with a given role
	 * @param role
	 * @return ArrayList of Person
	 */
	ArrayList<Person> findByRole(String role);
	
	/**
	 * Save person to "person" table as new record
	 * @param p
	 */
	void save(Person p);
	
	/**
	 * Find a person by ID
	 * @param studId
	 * @return Person object
	 */
	Person findById(int studId);
	
	/**
	 * Get all records in the "person" table
	 * @return ArrayList of Person
	 */
	ArrayList<Person> findAll();

}
