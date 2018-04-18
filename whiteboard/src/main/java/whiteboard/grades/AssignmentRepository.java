package whiteboard.grades;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import whiteboard.lecture.Lecture;
/**
 * Repository interface for assignment which extends CrudRepository with using Assignment, Long value
 * @author Sung Yealim
 *
 */
public interface AssignmentRepository extends Repository<Assignment, Long>{

	Assignment findByAssId(int glob_assId);
	//ArrayList<Assignment> findByCourseCode(String courseCode);
	Assignment findByCourseCode(String courseCode);
	void save(Assignment assignment);
	ArrayList<Assignment> findAll();
	//ArrayList<Assignment> findByCourseCode(String glob_courseCode);
	
}