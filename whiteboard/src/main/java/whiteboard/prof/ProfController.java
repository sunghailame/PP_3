package whiteboard.prof;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import whiteboard.course.Course;
import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
import whiteboard.lecture.Lecture;
import whiteboard.lecture.LectureRepository;
import whiteboard.login.Person;

@Controller
public class ProfController {
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	
	@Autowired
	private LectureRepository lectureRepository;
	
    @GetMapping("/prof/prof_home")
    public String prof_home_get(@CookieValue("person") String person, Model model) {
    	Person prof = new Person();
		prof.parseStringData(person.split("===="));
		//profRepository.save(prof);
		
		ArrayList<Enrollment> courses = new ArrayList<>();
		
		ArrayList<Enrollment> enrolled = enrollmentRepository.findAll();
		Iterator<Enrollment> e_cur = enrolled.iterator();
		
		while(e_cur.hasNext()) {
			Enrollment temp_prof = e_cur.next();
			if(prof.id == temp_prof.id) {
				courses.add(temp_prof);
			}
		}
		
		model.addAttribute("message", "Hello "+prof.name+"!");
		model.addAttribute("courses",courses);
        return "prof/prof_home";
    }
    
     @PostMapping("/prof/prof_home")
    public String prof_home_post(@ModelAttribute Person person, Course course, Model model) {
    	 
    	 model.addAttribute("message", "");
    	 return "login/greeting";
    }
     
     @GetMapping("/prof/course_page")
     public String course_page_get(@ModelAttribute Person person, Model model) {
     	 ArrayList<Lecture> lectures_temp = lectureRepository.findAll();
     	 ArrayList<ViewLecture> lectures = new ArrayList<>();
     	 Iterator<Lecture> lec_cur = lectures_temp.iterator();
     	 while(lec_cur.hasNext()) {
     		 Lecture lecture = (Lecture)lec_cur.next();
     	 }
     	 
     	 model.addAttribute("message", "");
     	 return "prof/course_page";
     }
     @PostMapping("/prof/course_page")
     public String course_page_post(@ModelAttribute Person person, Model model) {
     	 
     	 model.addAttribute("message", "");
     	 return "login/greeting";
     }
     
     
     
     
     //TODO: Add the logic to make this stuff work using Lecture and ViewLecture/FormWrapper
     @GetMapping("/prof/new_lecture")
     public String new_lecture_get(@ModelAttribute Person person, Model model) {
     	 model.addAttribute("message", "");
     	 return "prof/new_lecture";
     }
     @PostMapping("/prof/new_lecture")
     public String new_lecture_post(@ModelAttribute Person person, Model model) {
     	 model.addAttribute("message", "");
     	 return "prof/prof_home";
     }
     
     @GetMapping("/prof/view_lecture")
     public String view_lecture_get(@ModelAttribute Person person, Model model) {
     	 model.addAttribute("message", "");
     	 return "prof/new_lecture";
     }
     @PostMapping("/prof/view_lecture")
     public String view_lecture_post(@ModelAttribute Person person, Model model) {
     	 model.addAttribute("message", "");
     	 return "prof/prof_home";
     }
     
}
