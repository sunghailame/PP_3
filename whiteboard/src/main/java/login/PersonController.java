package login;

import org.json.JSONException;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonController {
	@Autowired

	private PersonRepository PersonRepository;
	@Autowired
    public PersonController(PersonRepository pr) {
        this.PersonRepository = pr;
    }
	@GetMapping("/whiteboard")
	public String login_main(@ModelAttribute Person user, Model model) {
		model.addAttribute("user", new Person());
		return "whiteboard";
	}

	@PostMapping("/whiteboard")
	public String home_from_login(@ModelAttribute Person user) {
		//TODO: Change name to role
//		Person p = PersonRepository.findById(user.id);
//		if (p.role.contains("admin")) {
//			return "admin/admin_home";
//		} else if (p.role.contains("prof")) {
//			return "prof/prof_home";
//		} else if (p.role.contains("student")) {
//			return "student/student_home";
//		}
		if(user.username == "???") {
			return "login/signup";
		}
		
		return "login/greeting";
	}
	
	
	@GetMapping("/login/signup")
	public String signup_from_login(@ModelAttribute Person user, Model model) {

		System.out.println("hello2");
		
		model.addAttribute("user", new Person());
		
		return "login/signup";
	}

	@PostMapping("/login/signup")
	public String login_from_signup(@ModelAttribute Person user){
		user.id = (long) 456;
		System.out.println(user.toString());
		PersonRepository.save(user);
		System.out.println(user.toString());
		return "whiteboard";
	}

}