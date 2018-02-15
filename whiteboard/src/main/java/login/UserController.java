package login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
	@Autowired
	
	private UserRepository userRepository;
	
    @GetMapping("/login/signup")
    public String signup_from_login(Model model, User user) {
		User n = new User();
    	model.addAttribute("user", n);
		//userRepository.save(n);
        return "login/signup";
    }
    
     @PostMapping("/login/signup")
    public String login_from_signup(@ModelAttribute User user) {
        return "whiteboard";
    }
     
    @GetMapping("/whiteboard")
    public String login_main(@ModelAttribute User user) {
    	return "whiteboard";
    }
    
   @PostMapping("/whiteboard")
   public String home_from_login(@ModelAttribute User user) {
       return "login/greeting";
   }

}