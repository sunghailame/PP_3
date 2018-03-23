package whiteboard.prof;

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
     		 if(this.glob_profId == lecture.profId && this.glob_courseCode == lecture.courseCode) {
     			 ViewLecture l = new ViewLecture(lecture.title, lecture.date, lecture.courseCode, false, lecture.profId);
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
     public String course_page_post(@ModelAttribute Person person, String choose_course, Model model) {
    	 System.out.println("post on course_page");
 		return "admin/admin_home";
 	}
     
     @GetMapping("/prof/new_lecture")
     public String new_lecture_get(@ModelAttribute Person person, Model model) {
     	 Lecture new_lecture = new Lecture();
     	 new_lecture.id = 0;
     	 new_lecture.profId = person.id;
     	 new_lecture.courseCode = this.glob_courseCode;
     	 java.util.Date getCur = new java.util.Date();
     	 new_lecture.date = new java.sql.Date(getCur.getTime());
     	 model.addAttribute("lecture", new_lecture);
    	 
    	 model.addAttribute("message", "");
     	 return "prof/new_lecture";
     }
     @PostMapping("/prof/new_lecture")
     public String new_lecture_post(@ModelAttribute Person person, @ModelAttribute Lecture lecture, Model model) {
     	 this.lectureRepository.save(lecture);
    	 model.addAttribute("message", "");
     	 return "prof/course_page";
     }
     
     @GetMapping("/prof/view_lecture")
     public String view_lecture_get(@ModelAttribute Person person, Model model) {
     	 model.addAttribute("message", "");
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
