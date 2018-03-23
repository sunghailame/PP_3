
package whiteboard.lecture;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


public interface LectureRepository extends Repository<Lecture, Long> {
		//Enrollment findByID(String id);
			
		void save(Lecture e);

		ArrayList<Lecture> findAll();
}