package whiteboard.admin;

import whiteboard.course.Course;
import whiteboard.course.CourseRepository;
import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
import whiteboard.login.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private EnrollmentRepository enrollmentRepository;

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
	public String login_from_signup(@ModelAttribute Person admin) {
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
		model.addAttribute("message","");
		return "admin/create_course";
	}
	
//	@GetMapping("/admin/delete_course")
//	public String delete_course_from_admin(Course course, Model model) {
//		adminRepository.deleteByNameIn(course.course_name);
//		return "admin/delete_course";
//	}

	@DeleteMapping("/admin/delete_course")
	public ResponseEntity<?> deleteCoures(@PathVariable(value = "CourseCode") String courseCode) {
		Course course = adminRepository.deleteByNameIn(courseCode);
		adminRepository.delete(course);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/admin/create_course")
	public String admin_home_from_create_course(@ModelAttribute Course course, BindingResult result, Model model) {
		System.out.println(course.toString());
		this.courseRepository.save(course);
		model.addAttribute("message", "Created course!");
		return "admin/create_course";
	}
	
	@GetMapping("/admin/enroll_student")
	public String enroll_student_from_admin(Model model) {
		Enrollment enrollment = new Enrollment();
		model.addAttribute("enrollment", enrollment);
		model.addAttribute("message","");
		return "admin/enroll_student";
	}
	
	@PostMapping("/admin/enroll_student")
	public String admin_home_from_enroll_student(@ModelAttribute Enrollment enrollment, BindingResult result, Model model) {
		System.out.println(enrollment.toString());
		this.enrollmentRepository.save(enrollment);
		model.addAttribute("message", "Enrolled Student!");
		return "admin/enroll_student";
	}
	
	

}
