package whiteboard.admin;

import org.springframework.data.repository.CrudRepository;

import whiteboard.course.Course;
import whiteboard.login.*;

public interface AdminRepository extends CrudRepository<Person, Long>{

//	void save (Person a);
	//Course deleteByNameIn(String names);

	//void delete(Course course);
}
