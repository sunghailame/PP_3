package whiteboard.student;

import whiteboard.login.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

  @GetMapping("/student/student_home")
    public String signup_from_login(@CookieValue("person") String person, Model model) {
	  	String[] dataSplit = person.split("=");
	  	Person stud = new Person();
	  	stud.id = Integer.parseInt(dataSplit[0]);
	  	stud.name = dataSplit[1];
	  	stud.username = dataSplit[2];
	  	stud.email = dataSplit[3];
	  	stud.role = dataSplit[4];
	  	stud.password = dataSplit[5];
    	model.addAttribute("username", stud.username);
    	model.addAttribute("name", stud.name);
        return "student/student_home";
    }
    
     @PostMapping("/student/student_home")
    public String login_from_signup(@ModelAttribute Person person) {
        return "login/greeting";
    }
}

