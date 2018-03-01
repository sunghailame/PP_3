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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PersonController {
	@Autowired

	private PersonRepository PersonRepository;

	@GetMapping("/whiteboard")
	public String login_main(@ModelAttribute Person user) {
		return "whiteboard";
	}

	@PostMapping("/whiteboard")
	public String home_from_login(HttpServletResponse response, @ModelAttribute Person person) {
		try {
			Person p = PersonRepository.findByUsername(person.username);
			
			if (person.password.equals(p.getPassword())) {
				Cookie passData = new Cookie("person",p.toStringData());
				System.out.println(p.toString());
				passData.setMaxAge(10000);
				response.addCookie(passData);
				if (p.role.contains("admin")) {
					return "redirect:/admin/admin_home";
				} else if (p.role.contains("prof")) {
					return "redirect:/prof/prof_home";
				} else if (p.role.contains("student")) {
					return "redirect:/student/student_home";
				}
			} else {
				return "redirect:login/error";
			}

			return "redirect:login/greeting";
		} catch (NullPointerException E) {
			return "redirect:login/error";
		}
	}

	@GetMapping("/login/signup")
	public String signup_from_login(Model model) {
		Person person = new Person();
		model.addAttribute("person", person);
		return "login/signup";
	}

	@PostMapping("/login/signup")
	public String login_from_signup(@ModelAttribute Person person, BindingResult result) {
		if (person.username.equals("")) {
			return "login/signup";
		} else {
			this.PersonRepository.save(person);
			return "whiteboard";
		}
	}
	

}