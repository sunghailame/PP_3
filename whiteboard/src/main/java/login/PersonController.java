package login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonController {
	@Autowired

	private PersonRepository userRepository;

	@GetMapping("/whiteboard")
	public String login_main(@ModelAttribute Person user) {
		return "whiteboard";
	}

	@PostMapping("/whiteboard")
	public String home_from_login(@ModelAttribute Person user) {
		//TODO: Change name to role
		/*if (user.name.contains("admin")) {
			return "admin/admin_home";
		} else if (user.name.contains("prof")) {
			return "prof/prof_home";
		} else if (user.name.contains("student")) {
			return "student/student_home";
		}*/
		return "login/greeting";
	}
	
	@GetMapping("/login/signup")
	public String signup_from_login(@ModelAttribute Person user, Model model) {
		Person n = new Person();
		model.addAttribute("user", n);
		// userRepository.save(n);
		return "login/signup";
	}

	@PostMapping("/login/signup")
	public String login_from_signup(@ModelAttribute Person user) {
		return "whiteboard";
	}

}