
package whiteboard.enrollment;

import org.springframework.data.repository.Repository;


public interface EnrollmentRepository extends Repository<Enrollment, Long> {
		//Enrollment findByID(String id);
			
		void save(Enrollment e);
}