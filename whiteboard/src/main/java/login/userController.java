package login;

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
    public String signup_from_login(Model model, user user) {
		user n = new user();
    	model.addAttribute("user", n);
		userRepository.save(n);
        return "login/signup";
    }
    
     @PostMapping("/login/signup")
    public String login_from_signup(@ModelAttribute user user) {
        return "whiteboard";
    }
     
    @GetMapping("/whiteboard")
    public String login_main(@ModelAttribute user user) {
    	return "whiteboard";
    }
    
   @PostMapping("/whiteboard")
   public String home_from_login(@ModelAttribute user user) {
       return "login/greeting";
   }

}