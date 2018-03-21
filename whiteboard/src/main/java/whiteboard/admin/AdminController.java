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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		Person admin = new Person();
		admin.parseStringData(person.split("="));
		adminRepository.save(admin);
		model.addAttribute("message", "Hello "+admin.name+"!");
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
		this.courseRepository.save(course);
		model.addAttribute("message", "Created course!");
		return "admin/admin_home";
	}
	
	@GetMapping("/admin/enroll_student")
	public String enroll_student_from_admin(Model model) {
		
		//Get list of all students, save to list of EnrollPerson type for form
		Iterable<Person> users_temp = adminRepository.findAll();
		ArrayList<EnrollPerson> users = new ArrayList<>();
		Iterator<Person> u_cur = users_temp.iterator();
		while(u_cur.hasNext()) {
			Person user = (Person) u_cur.next();
			EnrollPerson p = new EnrollPerson(user.id, false, user.username);
			users.add(p);
		}
		
		//Get list of all courses, save to list of EnrollCourse type for form
		Iterable<Course> course_temp = courseRepository.findAll();
		ArrayList<EnrollCourse> courses = new ArrayList<>();
		Iterator<Course> c_cur = course_temp.iterator();
		while(c_cur.hasNext()) {
			Course course = c_cur.next();
			EnrollCourse m = new EnrollCourse(course.course_code, course.course_name, false);
			courses.add(m);
		}
		
		//Create wrapper for sending to view with users and courses
		FormWrapper userList = new FormWrapper();
		userList.setUsers(users);
		userList.setCourses(courses);
		
		//Add the list to the view
		model.addAttribute("userList", userList);
		model.addAttribute("message","");
		return "admin/enroll_student";
	}
	
	//TODO: Add checking for if a user is already enrolled in a course - un-enroll them?
	@PostMapping("/admin/enroll_student")
	public String admin_home_from_enroll_student(@RequestParam("enrolled") List<String> users, @RequestParam("c_enrolled") List<String> courses, Model model) {
		
		try {
			
			//Parse the string data send back from the view
			//One course and students to enroll
			Iterator<String> u_cur = users.iterator();
			while(u_cur.hasNext()) {
				Iterator<String> c_cur = courses.iterator();
				EnrollCourse m = new EnrollCourse();
				if(c_cur.hasNext()) {
					String[] dataSplit = c_cur.next().split("=");
					m.courseCode = dataSplit[0];
					m.courseName = dataSplit[1];
					m.enrolled = Boolean.parseBoolean(dataSplit[2]);
				}
				
				String[] dataSplit = u_cur.next().split("=");
				EnrollPerson p = new EnrollPerson(Integer.parseInt(dataSplit[0]), Boolean.parseBoolean(dataSplit[1]), dataSplit[2]);
				Enrollment c = new Enrollment(p.id, m.courseCode, "1");
				System.out.println(c.toString());
				this.enrollmentRepository.save(c);
			}
			
			model.addAttribute("message", "Enrolled Students!");
		} catch (Exception E) {
			model.addAttribute("message", "Error enrolling. Try again.");
		}
		return "admin/admin_home";
	}
	
	

}
