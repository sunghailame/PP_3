package whiteboard.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


public interface StudentRepository extends Repository<Student, Long> {
	
	void save(Student s);
}
