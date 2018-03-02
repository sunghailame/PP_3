package whiteboard.admin;

import whiteboard.login.Person;
import whiteboard.login.PersonRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/admin_home")
    public String signup_from_login(@CookieValue("person") String person, Model model) {
	  	System.out.println(person);
    	String[] dataSplit = person.split("=");
	  	Person admin = new Person();
	  	admin.id = Integer.parseInt(dataSplit[0]);
	  	admin.name = dataSplit[1];
	  	admin.username = dataSplit[2];
	  	admin.email = dataSplit[3];
	  	admin.role = dataSplit[4];
	  	admin.password = dataSplit[5];
    	model.addAttribute("username", admin.username);
    	model.addAttribute("name", admin.name);
    	
        return "admin/admin_home";
    }
    
     @PostMapping("/admin/admin_home")
    public String login_from_signup(@ModelAttribute Person person) {
        return "login/greeting";
    }
}
