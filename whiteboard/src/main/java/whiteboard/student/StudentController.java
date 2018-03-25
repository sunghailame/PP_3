package whiteboard.student;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import whiteboard.course.Course;
import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
import whiteboard.lecture.Lecture;
import whiteboard.lecture.LectureRepository;
import whiteboard.login.Person;
import whiteboard.admin.EnrollCourse;
import whiteboard.admin.FormWrapper;
import whiteboard.login.PersonRepository;
import whiteboard.prof.ViewLecture;

@Controller
public class StudentController {
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private LectureRepository lectureRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	private int glob_profId;
	private String glob_courseCode;
	private String glob_lecTitle;
	private int glob_studId;
	
    @GetMapping("/student/student_home")
    public String stud_home_get(@CookieValue("person") String person, Model model) {
    	//Parse Cookie into correct Person object
    	Person stud = new Person();
		stud.parseStringData(person.split("===="));
		this.glob_studId = stud.id;
		
		//Retrieve list of courses the prof is enrolled in
		ArrayList<Enrollment> courses = new ArrayList<>();
		ArrayList<Enrollment> enrolled = enrollmentRepository.findAll();
		Iterator<Enrollment> e_cur = enrolled.iterator();
		while(e_cur.hasNext()) {
			Enrollment temp_prof = e_cur.next();
			if(stud.id == temp_prof.person_id) {
				courses.add(temp_prof);
			}
		}
		
		//Add objects to view
		model.addAttribute("message", "");
		model.addAttribute("courses", courses);
		model.addAttribute("person", person);
		
        return "student/student_home";
    }
    @PostMapping("/student/student_home")
    public String stud_home_post(@ModelAttribute Person person, @RequestParam("c_enrolled") String enroll_course, Model model) {
    	//Get selected course from post 
    	Enrollment course = new Enrollment();
    	course.parseStringData(enroll_course.split("===="));
    	this.glob_courseCode = course.course_code;
     	return "redirect:/student/course_page";
    }
     
     
     @GetMapping("/student/course_page")
     public String course_page_get(Model model) {
    	//Get list of lectures by courseCode and profId
    	 ArrayList<Lecture> lectures_temp = lectureRepository.findAll();
     	 ArrayList<ViewLecture> lectures = new ArrayList<>();
     	 Iterator<Lecture> lec_cur = lectures_temp.iterator();
     	 while(lec_cur.hasNext()) {
     		 Lecture lecture = (Lecture)lec_cur.next();
     		 if(this.glob_courseCode.equals(lecture.courseCode)) {
     			 ViewLecture l = new ViewLecture(lecture.title, lecture.date, lecture.courseCode, false, lecture.profId, lecture.link, lecture.details, lecture.attendance);
     			 lectures.add(l);
     		 }
     	 }
     	 
     	 //Attach the lectureList to the view
     	 FormWrapper lectureList = new FormWrapper();
     	 lectureList.setLectures(lectures);
     	 model.addAttribute("lectures", lectureList);
     	 return "student/course_page";

     }
     
     @PostMapping("/student/course_page")
<<<<<<< HEAD
     public String course_page_post(@ModelAttribute Person person, @ModelAttribute TakeAttendance attendance, @RequestParam("view_lecture") String view_lecture, Model model) {
=======
     public String course_page_post(@ModelAttribute Person person, @RequestParam("view_lecture") String view_lecture, Model model) {
>>>>>>> 216ba847acb090429f5c563f13cae2f0bbb9aa33
    	 System.out.println(view_lecture);
    	 Lecture retLec = new Lecture();
    	 retLec.parseStringData(view_lecture.split("===="));
    	 this.glob_lecTitle = retLec.title;
<<<<<<< HEAD
    	 //check if student marked attendance and if they did, send their attendance record to sql
    	 String marked = retLec.parseStringData(view_lecture.split("===="));
    	 if(marked.equals("attendance")) {
    		 this.attendanceRepository.save(attendance);
    	 }
=======
    	 
>>>>>>> 216ba847acb090429f5c563f13cae2f0bbb9aa33
 		return "redirect:/student/view_lecture";

 	}
    
     @GetMapping("/student/view_lecture")
     public String view_lecture_get(Model model) {
    	 Lecture lecture = new Lecture();
    	 ArrayList<Lecture> temp_lecture = lectureRepository.findAll();
    	 Iterator<Lecture> l_cur = temp_lecture.iterator();
    	 while(l_cur.hasNext()) {
    		 Lecture temp_lec = l_cur.next();
<<<<<<< HEAD
//    		 if(temp_lec.courseCode.equals(this.glob_courseCode) && 
//    				 temp_lec.title.equals(this.glob_lecTitle)) {
//    			 lecture.title = temp_lec.title;
//    			 lecture.date = temp_lec.date;
//    			 lecture.courseCode = temp_lec.courseCode;
//    			 lecture.details = temp_lec.details;
//    			 lecture.link = temp_lec.link;
//    			 lecture.profId = temp_lec.profId;
//    			 lecture.id = 0;
//    		 }
=======
    		 if(temp_lec.courseCode.equals(this.glob_courseCode) && 
    				 temp_lec.title.equals(this.glob_lecTitle)) {
    			 lecture.title = temp_lec.title;
    			 lecture.lecDate = temp_lec.lecDate;
    			 lecture.courseCode = temp_lec.courseCode;
    			 lecture.details = temp_lec.details;
    			 lecture.link = temp_lec.link;
    			 lecture.profId = temp_lec.profId;
    			 lecture.id = 0;
    			 lecture.attendance = temp_lec.attendance;
    		 }
>>>>>>> 216ba847acb090429f5c563f13cae2f0bbb9aa33
    	 }
    	 
    	 model.addAttribute("lecture", lecture);
    	 model.addAttribute("message","");
     	 return "student/view_lecture";

     }
     @PostMapping("/student/view_lecture")
<<<<<<< HEAD
     public String view_lecture_post(@ModelAttribute Person person, Model model) {
     	 model.addAttribute("message", "");
     	 return "student/student_home";
=======
     public String view_lecture_post(@ModelAttribute Person person, @RequestParam("attendance") String attendance, Model model) {
     		System.out.println(attendance);
    	 Lecture retLec = new Lecture();
     	String attend_check = retLec.parseStringData(attendance.split("===="));
     	
    	 if(attend_check == "attendance") {
    		 java.util.Date getCur = new java.util.Date();
         	 
    		 Attendance attend = new Attendance(0, retLec.courseCode, "1", new java.sql.Date(getCur.getTime()), retLec.profId, person.id, retLec.title);
    		 
    		 this.attendanceRepository.save(attend);
    	 }
    	 
    	 model.addAttribute("message", "");
     	 return "redirect:/student/course_page";
>>>>>>> 216ba847acb090429f5c563f13cae2f0bbb9aa33
     }
}
