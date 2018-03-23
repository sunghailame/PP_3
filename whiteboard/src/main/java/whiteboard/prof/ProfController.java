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
		
		ArrayList<Enrollment> courses = new ArrayList<>();
		
		ArrayList<Enrollment> enrolled = enrollmentRepository.findAll();
		Iterator<Enrollment> e_cur = enrolled.iterator();
		
		while(e_cur.hasNext()) {
			Enrollment temp_prof = e_cur.next();
			if(prof.id == temp_prof.person_id) {
				courses.add(temp_prof);
			}
		}
		
		model.addAttribute("message", "Hello "+prof.name+"!");
		model.addAttribute("courses", courses);
		model.addAttribute("person",person);
        return "prof/prof_home";
    }
     @PostMapping("/prof/prof_home")
    public String prof_home_post(@ModelAttribute Person person, Course course, Model model) {
    	 model.addAttribute("message", "");
    	 return "login/greeting";
    }
   
     @GetMapping("/prof/attendance_page")
     public String view_student_list(@ModelAttribute Person person, Model model) {
    	 return "prof/attendance_page";
     }
     @GetMapping("/prof/course_page")
     public String course_page_get(@ModelAttribute Person person, Model model) {
     	 ArrayList<Lecture> lectures_temp = lectureRepository.findAll();
     	 ArrayList<ViewLecture> lectures = new ArrayList<>();
     	 Iterator<Lecture> lec_cur = lectures_temp.iterator();
     	 while(lec_cur.hasNext()) {
     		 Lecture lecture = (Lecture)lec_cur.next();
     		 if(person.id == lecture.profId) {
     			 ViewLecture l = new ViewLecture(lecture.title, lecture.date, lecture.courseCode, false, lecture.profId);
     			 lectures.add(l);
     		 }
     	 }
     	 FormWrapper lectureList = new FormWrapper();
     	 lectureList.setLectures(lectures);
     	 model.addAttribute("lectures", lectureList);
     	 model.addAttribute("message", "");
     	 return "prof/course_page";
     }
     @PostMapping("/prof/course_page")
     public String course_page_post(@ModelAttribute Person person, String view_lecture, Model model) {
    	 String lectureTitle;
    	 int profId;
    	 String courseCode;
    	 
    	 try {
 			String[] splitResponse = view_lecture.split("====");
 			lectureTitle = splitResponse[0];
 			courseCode = splitResponse[2];
 			profId = Integer.parseInt(splitResponse[4]);
 			
 			ArrayList<Lecture> lectures = lectureRepository.findAll();
 			Iterator<Lecture> l_cur = lectures.iterator();
 			
 			while(l_cur.hasNext()) {
 				Lecture lec_temp = l_cur.next();
 				if(lec_temp.courseCode.equals(courseCode) && lec_temp.profId == profId &&
 						lec_temp.title.equals(courseCode)) {
 					
 					//Add to lecture object to send to view
 			}
 			
 			//Get attendance list
 				
 			model.addAttribute("message", "");
 			}
 		} catch (Exception E) {
 			model.addAttribute("message", "Error displaying lectures. Try again.");
 		}
 		return "admin/admin_home";
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
