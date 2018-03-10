package whiteboard.prof;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import whiteboard.login.Person;

@Controller
public class ProfController {
	
    @GetMapping("/prof/prof_home")
    public String signup_from_login(@CookieValue("person") String person, Model model) {
	  	String[] dataSplit = person.split("=");
	  	Person prof = new Person();
	  	prof.id = Integer.parseInt(dataSplit[0]);
	  	prof.name = dataSplit[1];
	  	prof.username = dataSplit[2];
	  	prof.email = dataSplit[3];
	  	prof.role = dataSplit[4];
	  	prof.password = dataSplit[5];
    	model.addAttribute("username", prof.username);
    	model.addAttribute("name", prof.name);
        return "prof/prof_home";
    }
    
     @PostMapping("/prof/prof_home")
    public String login_from_signup(@ModelAttribute Person person) {
        return "login/greeting";
    }
}
