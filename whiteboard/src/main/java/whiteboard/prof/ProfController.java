package whiteboard.prof;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import whiteboard.login.Person;

public class ProfController {
    @GetMapping("/prof/prof_home")
    public String signup_from_login(@CookieValue("username") String username, Model model) {
    	model.addAttribute("username",username);
        return "prof/prof_home";
    }
    
     @PostMapping("/prof/prof_home")
    public String login_from_signup(@ModelAttribute Person person) {
        return "login/greeting";
    }
}
