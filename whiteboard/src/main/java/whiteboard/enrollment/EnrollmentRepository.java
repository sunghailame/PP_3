
package whiteboard.enrollment;

import org.springframework.data.repository.Repository;


public interface EnrollmentRepository extends Repository<Enrollment, Long> {
		//Enrollment findByID(String id);
			
		void save(Enrollment e);
}

<<<<<<< HEAD
=======


>>>>>>> b3a366d708bb8ae732def1d0c11264de766105ae
