package whiteboard.grades;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
/**
 * Repository interface for assignment which extends CrudRepository with using Assignment, Long value
 * @author Sung Yealim
 *
 */
public interface AssignmentRepository extends Repository<Assignment, Long>{

	Assignment findByAssId(int glob_assId);
	Assignment findByCourseCode(String courseCode);
	void save(Assignment assignment);

}