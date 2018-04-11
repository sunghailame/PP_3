package whiteboard.course;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

	Course findByCourseCode(String courseCode);
	
}
