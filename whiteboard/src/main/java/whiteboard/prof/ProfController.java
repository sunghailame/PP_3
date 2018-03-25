package whiteboard.prof;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
//import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import whiteboard.course.Course;
import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
import whiteboard.lecture.Lecture;
import whiteboard.lecture.LectureRepository;
import whiteboard.login.Person;
import whiteboard.admin.EnrollCourse;
import whiteboard.admin.FormWrapper;
import whiteboard.login.PersonRepository;

@Controller
public class ProfController {
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private LectureRepository lectureRepository;
	@Autowired
	private PersonRepository personRepository;
	
	private int glob_profId;
	private String glob_courseCode;
	private String glob_lecTitle;
	
	private static SessionFactory factory; 
	
    @GetMapping("/prof/prof_home")
    public String prof_home_get(@CookieValue("person") String person, Model model) {
    	//Parse Cookie into correct Person object
    	Person prof = new Person();
		prof.parseStringData(person.split("===="));
		this.glob_profId = prof.id;
		
		//Retrieve list of courses the prof is enrolled in
		ArrayList<Enrollment> courses = new ArrayList<>();
		ArrayList<Enrollment> enrolled = enrollmentRepository.findAll();
		Iterator<Enrollment> e_cur = enrolled.iterator();
		while(e_cur.hasNext()) {
			Enrollment temp_prof = e_cur.next();
			if(prof.id == temp_prof.person_id) {
				courses.add(temp_prof);
			}
		}
		
		//Add objects to view
		model.addAttribute("message", "");
		model.addAttribute("courses", courses);
		model.addAttribute("person", person);
		
        return "prof/prof_home";
    }
    @PostMapping("/prof/prof_home")
    public String prof_home_post(@ModelAttribute Person person, @RequestParam("c_enrolled") String enroll_course, Model model) {
    	//Get selected course from post 
    	Enrollment course = new Enrollment();
    	course.parseStringData(enroll_course.split("===="));
    	this.glob_courseCode = course.course_code;
     	return "redirect:/prof/course_page";
    }
     
     
     @GetMapping("/prof/course_page")
     public String course_page_get(Model model) {
    	//Get list of lectures by courseCode and profId
    	 ArrayList<Lecture> lectures_temp = lectureRepository.findAll();
     	 ArrayList<ViewLecture> lectures = new ArrayList<>();
     	 Iterator<Lecture> lec_cur = lectures_temp.iterator();
     	 while(lec_cur.hasNext()) {
     		 Lecture lecture = (Lecture)lec_cur.next();
     		 if(this.glob_profId == lecture.profId && this.glob_courseCode.equals(lecture.courseCode)) {
     			 ViewLecture l = new ViewLecture(lecture.title, lecture.lecDate, lecture.courseCode, false, lecture.profId, lecture.link, lecture.details, lecture.attendance);
     			 lectures.add(l);
     		 }
     	 }
     	 
     	 //Attach the lectureList to the view
     	 FormWrapper lectureList = new FormWrapper();
     	 lectureList.setLectures(lectures);
     	 model.addAttribute("lectures", lectureList);
     	 return "prof/course_page";
     }
     
     @PostMapping("/prof/course_page")
     public String course_page_post(@ModelAttribute Person person, @RequestParam("view_lecture") String view_lecture, Model model) {
    	 System.out.println("post on course_page");
    	 System.out.println(view_lecture);
    	 Lecture retLec = new Lecture();
    	 
    	 String attendance = retLec.parseStringData(view_lecture.split("===="));
    	 this.glob_lecTitle = retLec.title;
    	 //Session session = factory.openSession();
    	 if(attendance.equals("attendance")) {
    		 Lecture lec = lectureRepository.findByTitleAndLecDateAndCourseCodeAndDetailsAndLinkAndProfId(retLec.title, retLec.lecDate, retLec.courseCode, retLec.details, retLec.link, retLec.profId);
    		 lec.setAttendance(true);
    		 lectureRepository.save(lec);
    		 //lectureRepository.setAttendance(true, retLec.title, retLec.date, retLec.courseCode, retLec.details, retLec.link, retLec.profId);
    		 return "redirect:/prof/course_page";
    		 //TODO: Update this lecture's attendance column in MySQL
    		 
    	 }
    	 
    	 System.out.println(retLec.toString());
    	 
 		return "redirect:/prof/view_lecture";
 	}
     
     @GetMapping("/prof/new_lecture")
     public String new_lecture_get(Model model) {
     	 Lecture new_lecture = new Lecture();
     	 model.addAttribute("lecture", new_lecture);
    	 model.addAttribute("message", "");
     	 return "prof/new_lecture";
     }
     @PostMapping("/prof/new_lecture")
     public String new_lecture_post(@ModelAttribute Person person, @ModelAttribute Lecture lecture, Model model) {
    	 lecture.id = 0;
     	 lecture.profId = this.glob_profId;
     	 lecture.courseCode = this.glob_courseCode;
     	 java.util.Date getCur = new java.util.Date();
     	 lecture.lecDate = new java.sql.Date(getCur.getTime());
     	 
     	 this.lectureRepository.save(lecture);
    	 model.addAttribute("message", "");
     	 return "redirect:/prof/course_page";
     }
     
     @GetMapping("/prof/view_lecture")
     public String view_lecture_get(Model model) {
    	 Lecture lecture = new Lecture();
    	 ArrayList<Lecture> temp_lecture = lectureRepository.findAll();
    	 Iterator<Lecture> l_cur = temp_lecture.iterator();
    	 while(l_cur.hasNext()) {
    		 Lecture temp_lec = l_cur.next();
    		 if(temp_lec.courseCode.equals(this.glob_courseCode) && temp_lec.profId == this.glob_profId && 
    				 temp_lec.title.equals(this.glob_lecTitle)) {
    			 lecture.title = temp_lec.title;
    			 lecture.lecDate = temp_lec.lecDate;
    			 lecture.courseCode = temp_lec.courseCode;
    			 lecture.details = temp_lec.details;
    			 lecture.link = temp_lec.link;
    			 lecture.profId = temp_lec.profId;
    			 lecture.id = 0;
    		 }
    	 }
    	 
    	 model.addAttribute("lecture",lecture);
    	 model.addAttribute("message","");
     	 return "prof/view_lecture";
     }
     @PostMapping("/prof/view_lecture")
     public String view_lecture_post(@ModelAttribute Person person, Model model) {
     	 model.addAttribute("message", "");
     	 return "prof/prof_home";
     }
     
     @GetMapping("/prof/attendance_page")
     public String view_student_get(@ModelAttribute Person person, Model model) {
//    	 if(person.role.toUpperCase().contains("STUDENT")) {
//    	 Iterable<Person> students = (Iterable<Person>) personRepository.findByRole(person.role);
//    	 }
    	 
    	 return "prof/attendance_page";
     }
     
     @PostMapping("/prof/attendance_page")
     public String view_student_post(@ModelAttribute Person person, Model model) {
    	 return "prof/attendance_page";
     }
     
}
