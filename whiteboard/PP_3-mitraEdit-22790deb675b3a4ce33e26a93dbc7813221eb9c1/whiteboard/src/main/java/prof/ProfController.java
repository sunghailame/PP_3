package prof;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import login.Person;

public class ProfController {
    @GetMapping("/prof/prof_home")
    public String signup_from_login(Model model, Person user) {
        return "prof/prof_home";
    }
    
     @PostMapping("/prof/prof_home")
    public String login_from_signup(@ModelAttribute Person user) {
        return "login/greeting";
    }
}
