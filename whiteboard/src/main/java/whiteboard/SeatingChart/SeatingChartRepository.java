
package whiteboard.SeatingChart;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


public interface SeatingChartRepository extends Repository<SeatingChart, Long> {
		
	//	@Modifying
		//@Query("UPDATE Lecture lec set lec.attendance = :attendance WHERE lec.title = :title, lec.date = :date, lec.courseCode = :coursecode, lec.details = :details, lec.link = :link, lec.profId = :profId")
	//	int setAttendance(@Param("attendance") boolean attendance, @Param("title") String title, @Param("date") Date date, @Param("coursecode") String coursecode, @Param("details") String details, @Param("link") String link, @Param("profId") int profId);
		
	//Lecture findByTitleAndLecDateAndCourseCodeAndDetailsAndLinkAndProfId(String title, Date date, String courseCode, String details, String link, int profId);	
		
	void save(ArrayList<SeatingChart> seatingList);

	ArrayList<SeatingChart> findAll();

	SeatingChart findByLectureId(int lectureId);
}