package whiteboard.grades;

import org.springframework.data.repository.CrudRepository;
/**
 * Repository interface for assignment which extends CrudRepository with using Assignment, Long value
 * @author Sung Yealim
 *
 */
public interface AssignmentRepository extends CrudRepository<Assignment, Long>{

}