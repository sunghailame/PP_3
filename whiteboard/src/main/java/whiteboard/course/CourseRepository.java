package whiteboard.course;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;




public interface CourseRepository extends CrudRepository<Course, Long> {

	//Iterable<Course> findAll();
	//void save(Course c);
	
}
