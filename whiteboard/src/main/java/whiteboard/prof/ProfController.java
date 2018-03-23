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

import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
import whiteboard.login.Person;

@Controller
public class ProfController {
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	
    @GetMapping("/prof/prof_home")
    public String signup_from_login(@CookieValue("person") String person, Model model) {
    	Person prof = new Person();
		prof.parseStringData(person.split("="));
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
    public String login_from_signup(@ModelAttribute Person person, Model model) {
    	 
    	 model.addAttribute("message", "");
    	 return "login/greeting";
    }
}
