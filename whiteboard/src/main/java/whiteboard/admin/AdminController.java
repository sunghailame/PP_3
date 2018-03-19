package whiteboard.admin;

import whiteboard.course.Course;
import whiteboard.course.CourseRepository;
import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
import whiteboard.login.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;


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
		
		String[] dataSplit = person.split("=");
		Person admin = new Person();
		admin.id = Integer.parseInt(dataSplit[0]);
		admin.name = dataSplit[1];
		admin.username = dataSplit[2];
		admin.email = dataSplit[3];
		admin.role = dataSplit[4];
		admin.password = dataSplit[5];
		model.addAttribute("message", "Hello "+admin.name+"!");
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
	
	/*@GetMapping("/admin/delete_course")
	public String delete_course_from_admin(Course course, Model model) {
		adminRepository.deleteByNameIn(course.course_name);
		return "admin/delete_course";
	}

	@DeleteMapping("/admin/delete_course")
	public ResponseEntity<?> deleteCoures(@PathVariable(value = "CourseCode") String courseCode) {
		Course course = adminRepository.deleteByNameIn(courseCode);
		adminRepository.delete(course);
		
		return ResponseEntity.ok().build();
	}
	*/
	@PostMapping("/admin/create_course")
	public String admin_home_from_create_course(@ModelAttribute Course course, BindingResult result, Model model) {
		System.out.println(course.toString());
		this.courseRepository.save(course);
		model.addAttribute("message", "Created course!");
		return "admin/create_course";
	}
	
	@GetMapping("/admin/enroll_student")
	public String enroll_student_from_admin(Model model) {
		//Enrollment enrollment = new Enrollment();
		//model.addAttribute("enrollment", enrollment);
		Iterable<Person> users_temp = adminRepository.findAll();
		ArrayList<DummyStudent> users = new ArrayList<>();
		Iterator<Person> iter = users_temp.iterator();
		while(iter.hasNext()) {
			Person user = (Person) iter.next();
			DummyStudent p = new DummyStudent(user.id, false, user.username);
			users.add(p);
		}
		
		Iterable<Course> course_temp = courseRepository.findAll();
		ArrayList<DummyCourse> courses = new ArrayList<>();
		Iterator<Course> iterate = course_temp.iterator();
		while(iterate.hasNext()) {
			Course course = iterate.next();
			DummyCourse m = new DummyCourse(course.course_code, course.course_name, false);
			courses.add(m);
		}
		
		FormWrapper userList = new FormWrapper();
		userList.setUsers(users);
		userList.setCourses(courses);
		model.addAttribute("userList", userList);
		model.addAttribute("message","");
		return "admin/enroll_student";
	}
	
	@PostMapping("/admin/enroll_student")
	public String admin_home_from_enroll_student(@RequestParam("enrolled") List<String> users, @RequestParam("c_enrolled") List<String> courses, Model model) {
		//System.out.println(enrollment.toString());
		//this.enrollmentRepository.save(enrollment);
		try {
			System.out.println(users);
			System.out.println(courses);
		
			Iterator<String> iter = users.iterator();
			while(iter.hasNext()) {
				
				Iterator<String> iterate = courses.iterator();
				DummyCourse m = new DummyCourse();
				if(iterate.hasNext()) {
					String[] dataSplit = iterate.next().split("=");
					m.courseCode = dataSplit[0];
					m.courseName = dataSplit[1];
					m.enrolled = Boolean.parseBoolean(dataSplit[1]);
				}
				
				String[] dataSplit = iter.next().split("=");
				DummyStudent p = new DummyStudent(Integer.parseInt(dataSplit[0]), Boolean.parseBoolean(dataSplit[1]),dataSplit[2]);
				Enrollment c = new Enrollment(p.id, m.courseCode, "1");
				this.enrollmentRepository.save(c);
			}
			
			model.addAttribute("message", "Enrolled Students!");
		} catch (Exception E) {
			model.addAttribute("message", "Error enrolling. Try again.");
		}
		return "admin/admin_home";
	}
	
	

}
