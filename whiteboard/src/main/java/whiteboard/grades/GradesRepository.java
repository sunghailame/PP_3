package whiteboard.grades;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import whiteboard.enrollment.Enrollment;
/**
 * Repository interface for grades which extends CrudRepository and uses type Grades, Long
 * @author Sung Yealim
 *
 */
public interface GradesRepository extends CrudRepository<Grades, Long>{

	Grades save(Grades grade);
	ArrayList<Grades> findAll();
	ArrayList<Grades> findByStudentId(int id);

}
