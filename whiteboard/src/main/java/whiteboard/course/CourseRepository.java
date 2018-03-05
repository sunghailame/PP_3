package whiteboard.course;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;




public interface CourseRepository extends Repository<Course, Long> {
	void save(Course c);
	
}
