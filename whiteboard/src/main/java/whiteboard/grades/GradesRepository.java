package whiteboard.grades;

import org.springframework.data.repository.CrudRepository;
/**
 * Repository interface for grades which extends CrudRepository and uses type Grades, Long
 * @author Sung Yealim
 *
 */
public interface GradesRepository extends CrudRepository<Grades, Long>{

}
