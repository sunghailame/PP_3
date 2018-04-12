package whiteboard.login;

import whiteboard.admin.*;
import whiteboard.student.*;
import whiteboard.prof.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 
 * Class that validates user login by comparing user's input with values from database and registers a new user 
 * @author Sydney Shile Teh
 *	
 */
@Controller
public class LoginController {
	@Autowired

	private PersonRepository PersonRepository;
	
	/**
	 * The login page(first page of the website）. It receives the user's login details.  
	 * @param user - A Person object that contains user's input
	 * @param model 
	 * @return whiteboard.html
	 */
	@GetMapping("/whiteboard")
	public String login_get(@ModelAttribute Person user, Model model) {
		model.addAttribute("message","");
		return "whiteboard";
	}
	
	/**
	 * Verify user's credential and direct the user to homepage according to user's role.
	 * Return error messages if user's credential cannot be found, or return link that directs user to adim, prof or student homepage. 
	 * @param response 
	 * @param person
	 * @param model
	 * @return admin_home.html, prof_home.html, student_homoe.html or whitboard.html 
	 */
	@PostMapping("/whiteboard")
	public String login_post(HttpServletResponse response, @ModelAttribute Person person, Model model) {
		try {
			Person p = PersonRepository.findByUsername(person.username);
			
			if (person.password.equals(p.getPassword())) {
				Cookie passData = new Cookie("person",p.toStringData());
				passData.setMaxAge(10000);
				response.addCookie(passData);
				if (p.role.toUpperCase().contains("ADMIN")) {
					
					return "redirect:/admin/admin_home";
				} else if (p.role.toUpperCase().contains("PROF")) {
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

	/**
	 * A register page for new users to register themselves on the application. 
	 * @param model
	 * @return signup.html
	 */
	@GetMapping("/login/signup")
	public String signup_get(Model model) {
		Person person = new Person();
		model.addAttribute("person", person);
		model.addAttribute("message","");
		return "login/signup";
	}

	/**
	 * Validates the user's input after registration. 
	 * If valid, user will be added and return to login page. Else, throws error message and stays at the page. 
	 * @param person
	 * @param result
	 * @param model
	 * @return signup.html or whiteboard.html
	 */
	@PostMapping("/login/signup")
	public String signup_post(@ModelAttribute Person person, BindingResult result, Model model) {

		try {
			if (person.username.equals("")) {
				model.addAttribute("message","Error. Please try again.");
				return "login/signup";
			} else {
				this.PersonRepository.save(person);

				model.addAttribute("message","Please login.");
				return "whiteboard";
			}
		} catch (Exception E) {
			model.addAttribute("message","Error. Please try again with different credentials.");
			return "login/signup";
		}
	}
	

}