package whiteboard.student;

import java.util.ArrayList;

import org.springframework.data.repository.Repository;

import whiteboard.lecture.Lecture;

public interface AttendanceRepository extends Repository<Attendance, Long>{

	ArrayList<Attendance> findAll();
	void save(Attendance a);
	Attendance findByLectureIdAndStudId(int lectureId, int studId);
}



