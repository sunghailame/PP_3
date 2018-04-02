
package whiteboard.SeatingChart;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


public interface SeatingChartRepository extends Repository<SeatingChart, Long> {
	void save(SeatingChart add);

	ArrayList<SeatingChart> findAll();
	
	ArrayList<SeatingChart> findByLectureIdOrderByColumn(int lectureId);
}