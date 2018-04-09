package whiteboard.admin;

import whiteboard.course.Course;
import whiteboard.course.CourseRepository;
import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
import whiteboard.location.Location;
import whiteboard.location.LocationGenerator;
import whiteboard.location.LocationRepository;
import whiteboard.login.Person;
import whiteboard.login.PersonRepository;

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
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping("/admin/admin_home")
	public String admin_home_get(@CookieValue("person") String person, Model model) {
		Person admin = new Person();
		admin.parseStringData(person.split("===="));
		model.addAttribute("message", "Hello "+admin.name+"!");
		return "admin/admin_home";
	}

	@PostMapping("/admin/admin_home")
	public String admin_home_post(@ModelAttribute Person admin) {
		return "login/greeting";
	}

	@GetMapping("/admin/show_users")
	public String show_users_get(Person person, Model model) {
		ArrayList<Person> users = this.personRepository.findAll();
		model.addAttribute("users", users);
		return "admin/show_users";
	}

	@GetMapping("/admin/create_course")
	public String create_course_get(Model model) {
		Course course = new Course();
		LocationGenerator locationList = new LocationGenerator();
		locationList.locations = this.locationRepository.findAll();
		
		model.addAttribute("locations", locationList.locations);
		model.addAttribute("course", course);
		model.addAttribute("message","");
		return "admin/create_course";
	}
	
	@PostMapping("/admin/create_course")
	public String create_course_post(@ModelAttribute Course course, @RequestParam("location_select")String location, BindingResult result, Model model) {
		
		try {
			if(course.course_name == "") {
				model.addAttribute("message", "Error. Try again.");
				return "admin/create_course";
			} else {
				if(!location.contains("default")) {
					Location set_building = new Location();
					set_building.parseStringData(location.split("===="));
					course.setBuildingId(set_building.locationId);
					System.out.println(course);
					this.courseRepository.save(course);
					model.addAttribute("message", "Created course!");
					return "admin/admin_home";
				} else {
					return "admin/create_course";
				}
			}
		} catch(Exception e) {
			model.addAttribute("message", "Error. Try again.");
			return "admin/create_course";
		}
	}
//	@GetMapping("/admin/delete_student")
//	public String delete_student_from_course(Person person, Model model) {
//		adminRepository.deleteByNameIn(person.id);
//		return "admin/delete_student";
//	}
//
//	@DeleteMapping("/admin/delete_student")
//	public ResponseEntity<?> deleteStudent(@PathVariable(value = "ID") int id) {
//		Person person = adminRepository.deleteByNameIn(id);
//		adminRepository.delete(person);
//		
//		return ResponseEntity.ok().build();
//	}
	
	
	@GetMapping("/admin/enroll_student")
	public String enroll_student_get(Model model) {
		ArrayList<Person> prof_users = this.personRepository.findByRole("student");
		ArrayList<EnrollPerson> users = new ArrayList<>();
		Iterator<Person> u_cur = prof_users.iterator();
		while(u_cur.hasNext()) {
			Person user = u_cur.next();
			EnrollPerson p = new EnrollPerson(user.id, false, user.username, user.role);
			users.add(p);
		}
		
		//Get list of all courses, save to list of EnrollCourse type for form
		Iterable<Course> course_temp = courseRepository.findAll();
		ArrayList<EnrollCourse> courses = new ArrayList<>();
		Iterator<Course> c_cur = course_temp.iterator();
		while(c_cur.hasNext()) {
			Course course = c_cur.next();
			EnrollCourse m = new EnrollCourse(course.courseCode, course.course_name, false, course.buildingId);
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
	public String enroll_student_post(@RequestParam("enrolled") List<String> enroll_users, @RequestParam("c_enrolled") String enroll_course, Model model) {
		String courseCode;
		try {
			//Parse the string data send back from the view
			//One course and students to enroll
			String[] splitCourse = enroll_course.split("====");
			courseCode = splitCourse[0];
			
			Iterator<String> u_cur = enroll_users.iterator();
			while(u_cur.hasNext()) {
				
				String[] splitUser = u_cur.next().split("====");
				this.enrollmentRepository.save(new Enrollment(0,Integer.parseInt(splitUser[0]), courseCode, "1",splitUser[3]));
			}
			
			model.addAttribute("message", "Enrolled Students!");
		} catch (Exception E) {
			model.addAttribute("message", "Error enrolling. Try again.");
		}
		return "admin/admin_home";
	}
	
	@GetMapping("/admin/enroll_prof")
	public String enroll_prof_get(Model model) {
		ArrayList<Person> prof_users = this.personRepository.findByRole("prof");
		ArrayList<EnrollPerson> users = new ArrayList<>();
		Iterator<Person> u_cur = prof_users.iterator();
		while(u_cur.hasNext()) {
			Person user = u_cur.next();
			EnrollPerson p = new EnrollPerson(user.id, false, user.username, user.role);
			users.add(p);
		}
		
		
		//Get list of all courses, save to list of EnrollCourse type for form
		Iterable<Course> course_temp = this.courseRepository.findAll();
		ArrayList<EnrollCourse> courses = new ArrayList<>();
		Iterator<Course> c_cur = course_temp.iterator();
		while(c_cur.hasNext()) {
			Course course = c_cur.next();
			EnrollCourse m = new EnrollCourse(course.courseCode, course.course_name, false, course.buildingId);
			courses.add(m);
		}
		
		//Create wrapper for sending to view with users and courses
		FormWrapper userList = new FormWrapper();
		userList.setUsers(users);
		userList.setCourses(courses);
		
		//Add the list to the view
		model.addAttribute("userList", userList);
		model.addAttribute("message","");
		return "admin/enroll_prof";
	}
	
	//TODO: Add checking for if a user is already enrolled in a course - un-enroll them?
	@PostMapping("/admin/enroll_prof")
	public String enroll_prof_post(@RequestParam("enrolled") String enroll_user, @RequestParam("c_enrolled") String enroll_course, Model model) {
		String courseCode;
		try {
			//Parse the string data send back from the view
			//One course and students to enroll
			String[] splitCourse = enroll_course.split("====");
			courseCode = splitCourse[0];
			
				String[] splitUser = enroll_user.split("====");
				Enrollment checkEnroll = enrollmentRepository.findByCourseCodeAndPersonIdAndRole(courseCode, Integer.parseInt(splitUser[0]), splitUser[3]);
				
				if(checkEnroll == null) {
					this.enrollmentRepository.save(new Enrollment(0,Integer.parseInt(splitUser[0]), courseCode, "1",splitUser[3]));
			}
			
			model.addAttribute("message", "Enrolled Students!");
		} catch (Exception E) {
			model.addAttribute("message", "Error enrolling. Try again.");
		}
		return "admin/admin_home";
	}
	
	

}
