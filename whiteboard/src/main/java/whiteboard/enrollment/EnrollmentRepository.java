
package whiteboard.enrollment;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {
	


	/**
	 * Returns all entries in the DB for Enrollment as an Array List of Enrollment objects.
	 * @return ArrayList<Enrollment>
	 */
	ArrayList<Enrollment> findAll();

	/**
	 * Finds the occurrence of the CourseCode, PersonID, and Role provided in the DB and returns the matching items
	 * as an Enrollment object.
	 * @param course_code
	 * @param id
	 * @param role
	 * @return Enrollment
	 */
	Enrollment findByCourseCodeAndPersonIdAndRole(String course_code, int id, String role);

	/**
	 * Finds all occurrences of the CourseCode and Role provided in the DB and returns them as an ArrayList of Enrollment objects.
	 * @param course_code
	 * @param role
	 * @return ArrayList<Enrollment>
	 */
	ArrayList<Enrollment> findByCourseCodeAndRole(String course_code, String role);

	/**
	 * Finds all occurrences of the Role provided in the DB and returns them as an ArrayList of Enrollment objects.
	 * @param string
	 * @return ArrayList<Enrollment>
	 */
	ArrayList<Enrollment> findByRole(String string);

	static void deleteById(int id) {

		
	}


}