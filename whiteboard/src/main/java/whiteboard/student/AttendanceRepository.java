package whiteboard.student;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import whiteboard.lecture.Lecture;

public interface AttendanceRepository <Attendance, Long>{

	ArrayList<Lecture> findAll();
	void save(Attendance a);
}



