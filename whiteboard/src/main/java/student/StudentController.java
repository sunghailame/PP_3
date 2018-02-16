package student;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import login.User;

public class StudentController {
    @GetMapping("/student/student_home")
    public String signup_from_login(Model model, User user) {
        return "admin/admin_home";
    }
    
     @PostMapping("/student/student_home")
    public String login_from_signup(@ModelAttribute User user) {
        return "login/greeting";
    }
}

