package whiteboard.login;

import java.util.Map;

import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String login_main(@ModelAttribute Person user) {
		return "whiteboard";
	}

	@PostMapping("/whiteboard")
	public String home_from_login(@ModelAttribute Person user){
		
		try {
		Person p = PersonRepository.findByUsername(user.username);
		if (user.password.equals(p.getPassword())) {
				if (p.role.contains("admin")) {
					return "admin/admin_home";
				} else if (p.role.contains("prof")) {
					return "prof/prof_home";
				} else if (p.role.contains("student")) {
					return "student/student_home";
				}
			} else {
				return "login/error";
			}

			return "login/greeting";
		}
		catch (NullPointerException E) {
			return "login/error";
			
		}
	}

	@GetMapping("/login/signup")
	public String signup_from_login(Map<String, Object> model) {
		Person person = new Person();
		model.put("person", person);
		return "login/signup";
	}

	@PostMapping("/login/signup")
	public String login_from_signup(@Valid Person person, BindingResult result) {
		if (person.username.equals("")) {
			return "login/signup";
		} else {
			this.PersonRepository.save(person);
			return "whiteboard";
		}
	}

}