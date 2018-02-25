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
    public String signup_from_login(@CookieValue("username") String username, Model model) {
    	model.addAttribute("username",username);
        return "student/student_home";
    }
    
     @PostMapping("/student/student_home")
    public String login_from_signup(@ModelAttribute Person person) {
        return "login/greeting";
    }
}

