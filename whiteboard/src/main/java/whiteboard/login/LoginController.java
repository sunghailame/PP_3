package whiteboard.login;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	@Autowired

	private PersonRepository PersonRepository;

	@GetMapping("/whiteboard")
	public String login_main(@ModelAttribute Person user, Model model) {
		model.addAttribute("message","");
		return "whiteboard";
	}

	@PostMapping("/whiteboard")
	public String home_from_login(HttpServletResponse response, @ModelAttribute Person person, Model model) {
		try {
			Person p = PersonRepository.findByUsername(person.username);
			
			if (person.password.equals(p.getPassword())) {
				Cookie passData = new Cookie("person",p.toStringData());
				System.out.println(p.toString());
				passData.setMaxAge(10000);
				response.addCookie(passData);
				if (p.role.toUpperCase().contains("ADMIN")) {
					return "redirect:/admin/admin_home";
				} else if (p.role.toUpperCase().contains("PROFESSOR")) {
					return "redirect:/prof/prof_home";
				} else if (p.role.toUpperCase().contains("STUDENT")) {
					return "redirect:/student/student_home";
				}
			} else {
				model.addAttribute("message","Password/Username not found. Please try again.");
				return "whiteboard";
			}
			model.addAttribute("message","Password/Username not found. Please try again.");
			return "whiteboard";
		} catch (Exception E) {
			model.addAttribute("message","Password/Username not found. Please try again.");
			return "whiteboard";
		}
	}

	@GetMapping("/login/signup")
	public String signup_from_login(Model model) {
		Person person = new Person();
		model.addAttribute("person", person);
		model.addAttribute("message","");
		return "login/signup";
	}

	@PostMapping("/login/signup")
	public String login_from_signup(@ModelAttribute Person person, BindingResult result, Model model) {
		try {
			if (person.username.equals("")) {
				model.addAttribute("message","Error. Please try again.");
				return "login/signup";
			} else {
				this.PersonRepository.save(person);
				this.S
				model.addAttribute("message","Please login.");
				return "whiteboard";
			}
		} catch (Exception E) {
			model.addAttribute("message","Error. Please try again with different credentials.");
			return "login/signup";
		}
	}
	

}