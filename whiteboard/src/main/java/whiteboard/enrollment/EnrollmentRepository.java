
package whiteboard.enrollment;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


public interface EnrollmentRepository extends Repository<Enrollment, Long> {
		//Enrollment findByID(String id);
			
		void save(Enrollment e);

		ArrayList<Enrollment> findAll();

		Enrollment findByCourseCodeAndPersonIdAndRole(String course_code, int id, String role);
}