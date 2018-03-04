package whiteboard.admin;

import whiteboard.course.Course;
import whiteboard.course.CourseRepository;
import whiteboard.login.Person;
import whiteboard.login.PersonRepository;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;
	private CourseRepository courseRepository;

	// @GetMapping(path= "/login/signup")
	// public @ResponseBody String addUsers (@RequestParam int ID, @RequestParam
	// String name,
	// @RequestParam String email, @RequestParam String role, @RequestParam String
	// username) {
	// Person p = new Person();
	// p.setId(ID);
	// p.setName(name);
	// p.setEmail(email);
	// p.setRole(role);
	// p.setUsername(username);
	// adminRepository.save(p);
	// return "admin/show_users";
	//
	// }

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
		adminRepository.save(admin);

		return "admin/admin_home";
	}

	@PostMapping("/admin/admin_home")
	public String login_from_signup(@ModelAttribute Person person) {
		return "login/greeting";
	}

	@GetMapping("/admin/show_users")
	public String show_users_from_admin(Person person, Model model) {
		Iterable<Person> users = adminRepository.findAll();
		model.addAttribute("users", users);

		return "admin/show_users";
	}

	@GetMapping("/admin/create_course")
	public String create_course_from_admin(Model model) {
		Course course = new Course();
		model.addAttribute("course", course);
		return "admin/create_course";
	}

	@PostMapping("/admin/create_course")
	public String admin_home_from_create_course(@ModelAttribute Course course, BindingResult result) {
		
		this.courseRepository.save(course);
		return "admin/admin_home";
	}

}
