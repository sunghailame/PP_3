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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class controls the data and views for the admin role's pages.
 */
@Controller
public class AdminController {
	
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private PersonRepository personRepository;
	
	private int glob_EnrollId;
	private String glob_courseCode;
	
	/**
	 * Homepage for the administrator; routed from LoginController, contigent on user's role = admin
	 * @param person
	 * @param model
	 * @return admin_home.html
	 */
	@GetMapping("/admin/admin_home")
	public String admin_home_get(@CookieValue("person") String person, Model model) {
		Person admin = new Person();
		admin.parseStringData(person.split("===="));
		model.addAttribute("message", "Hello "+admin.name+"!");
		return "admin/admin_home";
	}

	/**
	 * Gets the list of all users from the DB and adds them to the model for viewing.
	 * @param person
	 * @param model
	 * @return show_users.html
	 */
	@GetMapping("/admin/show_users")
	public String show_users_get(Person person, Model model) {
		ArrayList<Person> users = this.personRepository.findAll();
		model.addAttribute("users", users);
		return "admin/show_users";
	}

	/**
	 * Sets up the data to show the create course prompt and loads a drop down list of possible locations.
	 * Add this info to the model for viewing.
	 * @param model
	 * @return create_course.html
	 */
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
	
	/**
	 * Gets the course information entered by the user in the post submission.
	 * Saves the new course (if entered correctly) into the DB.
	 * @param course
	 * @param location
	 * @param model
	 * @return admin_home.html (or create_course.html if incorrect data/exception)
	 */
	@PostMapping("/admin/create_course")
	public String create_course_post(@ModelAttribute Course course, @RequestParam("location_select")String location, Model model) {
		
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
	@GetMapping("/admin/withdraw_student")
	public String delete_student_from_course(Person person, Model model, Enrollment enrollment) {
		//Get list of all students, save to EnrollPerson object
				ArrayList<Person> prof_users = this.personRepository.findByRole("student");
				ArrayList<EnrollPerson> users = new ArrayList<>();
				Iterator<Person> u_cur = prof_users.iterator();
				while(u_cur.hasNext()) {
					Person user = u_cur.next();
					EnrollPerson p = new EnrollPerson(user.id, false, user.username, user.role);
					users.add(p);
					if(p.isEnrolled()) {
						enrollmentRepository.delete(new Enrollment(enrollment.id, p.getId(), glob_courseCode, enrollment.sectionNo, p.getRole()));
					}
					
					System.out.println(users);
				}
				
				//Get list of all courses, save to list of EnrollCourse object
				Iterable<Course> course_temp = courseRepository.findAll();
				ArrayList<EnrollCourse> courses = new ArrayList<>();
				Iterator<Course> c_cur = course_temp.iterator();
				while(c_cur.hasNext()) {
					Course course = c_cur.next();
					EnrollCourse m = new EnrollCourse(course.courseCode, course.course_name, false, course.buildingId);
					courses.add(m);
					System.out.println(courses);
				}
				
				//Create wrapper for sending to view with users and courses
				FormWrapper userList = new FormWrapper();
				userList.setUsers(users);
				userList.setCourses(courses);
				
				//Add the list to the view
				model.addAttribute("userList", userList);
				model.addAttribute("message","");
		
				return "admin/withdraw_student";
	}

	@DeleteMapping("/admin/withdraw_student")
	public String deleteStudent(Model model, @PathVariable(value = "ID") int id, @RequestParam("c_enrolled") String enroll_course, @RequestParam("enrolled") List<String> enroll_users) {
		String courseCode;
		try {
			//Parse the string data send back from the view
			//One course and students to enroll
			String[] splitCourse = enroll_course.split("====");
			courseCode = splitCourse[0];
			
			Iterator<String> u_cur = enroll_users.iterator();
			while(u_cur.hasNext()) {
				
				String[] splitUser = u_cur.next().split("====");
				Enrollment e = enrollmentRepository.findByCourseCodeAndPersonIdAndRole(splitUser[1],Integer.parseInt(splitUser[0]),splitUser[3]);
				this.enrollmentRepository.delete(new Enrollment(0,Integer.parseInt(splitUser[0]), courseCode, "1",splitUser[3]));

}
			model.addAttribute("message", "Deleted students from the course!");
		} catch (Exception E) {
			model.addAttribute("message", "Error deleting. Try again.");
		}
		return "/admin/admin_home";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete() {
		
	}
	/**
	 * Gets the list of all possible courses and students and adds them to the wrapper class.
	 * Adds this object to the model for viewing.
	 * @param model
	 * @return enroll_student.html
	 */
	@GetMapping("/admin/enroll_student")
	public String enroll_student_get(Model model) {
		//Get list of all students, save to EnrollPerson object
		ArrayList<Person> prof_users = this.personRepository.findByRole("student");
		ArrayList<EnrollPerson> users = new ArrayList<>();
		Iterator<Person> u_cur = prof_users.iterator();
		while(u_cur.hasNext()) {
			Person user = u_cur.next();
			EnrollPerson p = new EnrollPerson(user.id, false, user.username, user.role);
			users.add(p);
		}
		
		//Get list of all courses, save to list of EnrollCourse object
		Iterable<Course> course_temp = courseRepository.findAll();
		ArrayList<EnrollCourse> courses = new ArrayList<>();
		Iterator<Course> c_cur = course_temp.iterator();
		while(c_cur.hasNext()) {
			Course course = c_cur.next();
			EnrollCourse m = new EnrollCourse(course.courseCode, course.course_name, false, course.buildingId);
//			if(m.isEnrolled()) {
//				break;
//			}
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
	
	/**
	 * Gets the list of students/course from the post submission and saves the information to the DB.
	 * @param enroll_users
	 * @param enroll_course
	 * @param model
	 * @return admin_home.html
	 */
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
				Enrollment e = enrollmentRepository.findByCourseCodeAndPersonIdAndRole(splitUser[1],Integer.parseInt(splitUser[0]),splitUser[3]);
				Enrollment en = new Enrollment(0,Integer.parseInt(splitUser[0]), courseCode, "1",splitUser[3]);
//				if (splitUser[4].contains("deleteStudent")) {
//					this.enrollmentRepository.delete(en);
//				}
//				else if(splitUser.length == 4){
				this.enrollmentRepository.save(new Enrollment(0,Integer.parseInt(splitUser[0]), courseCode, "1",splitUser[3]));
//				}
}
			
			model.addAttribute("message", "Enrolled Students!");
		} catch (Exception E) {
			model.addAttribute("message", "Error enrolling. Try again.");
		}
		return "admin/admin_home";
	}
	
	/**
	 * Gets the list of all courses/professors and saves it the wrapper class.
	 * Attaches the object to the model for viewing.
	 * @param model
	 * @return enroll_prof.html
	 */
	@GetMapping("/admin/enroll_prof")
	public String enroll_prof_get(Model model) {
		//Get list of prof users
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
	
	/**
	 * Gets the list of professors/course enrolled and saves to the DB.
	 * @param enroll_user
	 * @param enroll_course
	 * @param model
	 * @return
	 */
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
