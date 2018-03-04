package whiteboard.enrollment;

import org.springframework.data.repository.Repository;

import whiteboard.login.Person;

public interface EnrollmentRepository extends Repository<Person, Long> {
		//Enrollment findByID(String id);
		
		
		void Enrollment(Enrollment e);
}

