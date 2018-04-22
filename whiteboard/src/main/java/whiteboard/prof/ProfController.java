package whiteboard.prof;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

import whiteboard.student.ViewAttendance;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jms.ObjectMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import whiteboard.course.Course;
import whiteboard.course.CourseRepository;
import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
import whiteboard.grades.Assignment;
import whiteboard.grades.AssignmentRepository;
import whiteboard.grades.Grades;
import whiteboard.grades.GradesRepository;
import whiteboard.lecture.Lecture;
import whiteboard.lecture.LectureRepository;
import whiteboard.location.Location;
import whiteboard.location.LocationGenerator;
import whiteboard.location.LocationRepository;
import whiteboard.login.Person;
import whiteboard.SeatingChart.SeatingChart;
import whiteboard.SeatingChart.SeatingChartRepository;
import whiteboard.SeatingChart.SeatingGenerator;
import whiteboard.admin.EnrollCourse;
import whiteboard.admin.FormWrapper;
import whiteboard.login.PersonRepository;
import whiteboard.notification.Notification;
import whiteboard.notification.NotificationGenerator;
import whiteboard.notification.NotificationRepository;
import whiteboard.Message.Message;
import whiteboard.student.Attendance;
import whiteboard.student.AttendanceRepository;
import org.springframework.context.MessageSource;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;


import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import whiteboard.storage.StorageService;

/**
 * A controller for professors. Professor normally has control over
 * upload/viewing lecture, take/viewing attendance, view the courses that he is
 * enrolled in, and post and grade assignments.
 * 
 * @author Sung Yealim
 *
 */
@Controller
public class ProfController {

	private final StorageService storageService;

	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private LectureRepository lectureRepository;

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private SeatingChartRepository seatingRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private CourseRepository courseRepository;


	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private GradesRepository gradesRepository;

	
	List<String> files = new ArrayList<String>();
	private final Path rootLocation = Paths.get("upload-dir");
	

	private int glob_profId;
	private int glob_lectureId;
	private String glob_courseCode;
	private String glob_lecTitle;
	private String glob_attendLec;
	private Date glob_Date;
	private String glob_Details;
	private String glob_Link;

	private int glob_assId;
	private String glob_assName;
	private int glob_percentage;
	private int glob_studId;
	private ArrayList<Grades> glob_grades;


	
	 @Autowired
	 public ProfController(StorageService storageService) {
		 this.storageService = storageService;
	 }

	/**
	 * This function gets mapping from professor home. It shows the courses that the
	 * professor is currently enrolled in.
	 * 
	 * @param person:
	 *            person object
	 * @param model:
	 *            to add attributes to view
	 * @return prof/prof_home
	 */
	@GetMapping("/prof/prof_home")
	public String prof_home_get(@CookieValue("person") String person, Model model) {
		// Parse Cookie into correct Person object
		Person prof = new Person();
		prof.parseStringData(person.split("===="));
		this.glob_profId = prof.id;

		// Retrieve list of courses the prof is enrolled in
		ArrayList<Enrollment> courses = new ArrayList<>();
		ArrayList<Enrollment> enrolled = enrollmentRepository.findAll();
		Iterator<Enrollment> e_cur = enrolled.iterator();
		while (e_cur.hasNext()) {
			Enrollment temp_prof = e_cur.next();
			if (prof.id == temp_prof.personId) {
				courses.add(temp_prof);
			}
		}

		// Add objects to view
		model.addAttribute("message", prof.name);
		model.addAttribute("courses", courses);
		// model.addAttribute("link", "prof/course_page");
		model.addAttribute("person", person);
		model.addAttribute("linkToCourse", "prof/course_page");

		return "prof/prof_home";
	}

	/**
	 * This function posts mapping to professor home. It will request parameter from
	 * professor's enrolled course to let the professor click on the course page
	 * he/she would want to view.
	 * 
	 * @param person:
	 *            object
	 * @param enroll_course:
	 *            selected course
	 * @param model
	 * @return prof/course_page
	 */
	@PostMapping("/prof/prof_home")
	public String prof_home_post(@ModelAttribute Person person, @RequestParam("c_enrolled") String enroll_course,
			Model model) {
		// Get selected course from post
		Enrollment course = new Enrollment();
		course.parseStringData(enroll_course.split("===="));
		this.glob_courseCode = course.courseCode;
		return "redirect:/prof/course_page";
	}
	
	
	/**
	 * This function gets mapping from course page. It gets the list of lectures by
	 * using the professor id and the course code that he/she is enrolled in. Then
	 * it will attach the lecture list to the view.
	 * 
	 * @param model
	 * @return prof/course_page
	 */
	@GetMapping("/prof/course_page")
	public String course_page_get(Model model) {
		// Get list of lectures by courseCode and profId
		ArrayList<Lecture> lectures_temp = lectureRepository.findAll();
		Iterator<Lecture> lec_cur = lectures_temp.iterator();
		ArrayList<Lecture> lectures = new ArrayList<>();
		// ArrayList<Assignment> assignments =
		// assignmentRepository.findByCourseCode(glob_courseCode);
		ArrayList<Assignment> ass_temp = assignmentRepository.findAll();
		Iterator<Assignment> ass_cur = ass_temp.iterator();
		ArrayList<Assignment> assignments = new ArrayList<>();

		while (lec_cur.hasNext()) {
			Lecture lecture = (Lecture) lec_cur.next();
			if (this.glob_profId == lecture.profId && this.glob_courseCode.equals(lecture.courseCode)) {
				lectures.add(lecture);
			}
		}

		// Attach the lectureList to the view
		FormWrapper lectureList = new FormWrapper();
		lectureList.setLectures(lectures);
		model.addAttribute("lectures", lectureList);

		ArrayList<Assignment> assignmentList = this.assignmentRepository.findByCourseCode(this.glob_courseCode);
		// assignmentList.setAssignment(this.assignmentRepository.findByCourseCode(this.glob_courseCode));
		model.addAttribute("assignments", assignmentList);

		return "prof/course_page";
	}
	

   

	/**
	 * This function posts mapping from course page. If the professor clicks on one
	 * of the course code that he/she would like to view, it will prompt him/her to
	 * the lecture page where they can view seating chart, attendance, and the
	 * grades.
	 * 
	 * @param person
	 * @param view_lecture
	 * @param model
	 * @return prof/view_lecture
	 */
	@PostMapping("/prof/course_page")
	public String course_page_post(@ModelAttribute Person person, @RequestParam("view_lecture") String[] view_lecture,
			Model model) {
		int view = 0;
		for (int x = 0; x < view_lecture.length; x++) {
			if (view_lecture[x].contains("====gradeThisOne")) {
				String[] splitter = view_lecture[x].split("====");
				this.glob_assId = Integer.parseInt(splitter[0]);
				view = 2;
			} else {
				Lecture retLec = new Lecture();
				retLec.parseStringData(view_lecture[x].split("===="));
				if (view_lecture[x].contains("====viewThisOne")) {
					this.glob_lecTitle = retLec.title;
					this.glob_lectureId = retLec.lectureId;
					view = 1;
				}
				if (view_lecture[x].contains("attendance") || view_lecture[x].contains("close")) {
					Lecture lec = lectureRepository.findByLectureId(retLec.lectureId);
					lec.setAttendance(retLec.openAttendance);
					lectureRepository.save(lec);
				}
			}
		}
		if (view == 1) {
			return "redirect:/prof/view_lecture";
		} else if (view == 2) {
			return "redirect:/prof/grades";
		} else {
			return "redirect:/prof/course_page";
		}

	}
	
	
	/**
	 * This function gets mapping from view location. It will find the course and
	 * locate the building assigned to it.
	 * 
	 * @param model
	 * @return prof/view_location
	 */
	@GetMapping("/prof/view_location")
	public String view_location_get(Model model) {
		Course findId = this.courseRepository.findByCourseCode(this.glob_courseCode);
		Location building = this.locationRepository.findByLocationId(findId.buildingId);
		LocationGenerator gen = new LocationGenerator();
		String mapView = gen.embedLink(building.latitude, building.longitude);
		model.addAttribute("link", mapView);
		model.addAttribute("building", building.building);
		return "prof/view_location";
	}

	@GetMapping("prof/chat")
	public String chat_get(Model model) {
		model.addAttribute("course", this.glob_courseCode);
		String username = this.personRepository.findById(this.glob_profId).username;
		model.addAttribute("username", username);
		model.addAttribute("courseCode", this.glob_courseCode);
		model.addAttribute("role", "prof");
		return "prof/chat";
	}

	@MessageMapping("/chat.sendMessage/{courseCode}")
	@SendTo("/topic/public/{courseCode}")
	public Message sendMessage(@DestinationVariable String courseCode, @Payload Message chatMessage) {
		return chatMessage;
	}

	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public Message addUser(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

		return chatMessage;
	}

	/**
	 * This function gets mapping from new lecture. It will look for all the
	 * students who are enrolled to the according course and generate a seating
	 * chart for that lecture.
	 * 
	 * @param model
	 * @return prof/new_lecture
	 */
	@GetMapping("/prof/new_lecture")
	public String new_lecture_get(Model model) {
		Lecture new_lecture = new Lecture();
		SeatingGenerator seating = new SeatingGenerator();
		ArrayList<Enrollment> students = this.enrollmentRepository.findByCourseCodeAndRole(this.glob_courseCode,
				"student");
		ArrayList<Person> lookup = this.personRepository.findAll();
		seating.setView(students, lookup, 0);
		model.addAttribute("seating", seating);
		model.addAttribute("lecture", new_lecture);
		model.addAttribute("message", "");
		return "prof/new_lecture";
	}

	/**
	 * This function posts mapping to new lecture. Professor would be able to view
	 * and assign students to the seating chart for the lecture as he wish.
	 * 
	 * @param person
	 * @param lecture
	 * @param seatingTable
	 * @param model
	 * @return prof/course_page
	 */
	@PostMapping("/prof/new_lecture")
	public String new_lecture_post(@ModelAttribute Person person, @ModelAttribute Lecture lecture,
			@RequestParam("seating_table") List<String> seatingTable, Model model) {
		lecture.lectureId = 0;
		lecture.profId = this.glob_profId;
		lecture.courseCode = this.glob_courseCode;
		lecture.openAttendance = false;
		java.util.Date getCur = new java.util.Date();
		lecture.lecDate = new java.sql.Date(getCur.getTime());
		this.lectureRepository.save(lecture);

		ArrayList<Enrollment> receivers = new ArrayList<Enrollment>();
		receivers = enrollmentRepository.findByCourseCodeAndRole(this.glob_courseCode, "student");
		for (Enrollment e : receivers) {
			Notification n = new Notification();
			NotificationGenerator ng = new NotificationGenerator(e.personId);
			n = ng.createNotification_newLecture(lecture.courseCode, lecture.title);
			notificationRepository.save(n);
		}

		Lecture findId = this.lectureRepository.findByTitleAndLecDateAndProfId(lecture.title, lecture.lecDate,
				lecture.profId);
		SeatingGenerator seating = new SeatingGenerator();

		seating.assign(seatingTable, findId.lectureId);
		Iterator<SeatingChart> cur_seat = seating.seatingList.iterator();
		while (cur_seat.hasNext()) {
			SeatingChart add = cur_seat.next();
			this.seatingRepository.save(add);
		}

		model.addAttribute("message", "");
		return "redirect:/prof/course_page";
	}

	/**
	 * In this function, professor will be able to view the lecture details and
	 * attendance for that lecture. It will also get the seating chart and the
	 * documents that the professor uploaded.
	 * 
	 * @param model
	 * @return prof/view_lecture
	 */
	@GetMapping("/prof/view_lecture")
	public String view_lecture_get(Model model) {
		Lecture lecture = new Lecture();
		ArrayList<Lecture> temp_lecture = lectureRepository.findAll();
		Iterator<Lecture> l_cur = temp_lecture.iterator();

		while (l_cur.hasNext()) {
			Lecture temp_lec = l_cur.next();
			// show the list of lectures
			if (temp_lec.courseCode.equals(this.glob_courseCode) && temp_lec.profId == this.glob_profId
					&& temp_lec.title.equals(this.glob_lecTitle)) {
				lecture.title = temp_lec.title;
				lecture.lecDate = temp_lec.lecDate;
				lecture.courseCode = temp_lec.courseCode;
				lecture.details = temp_lec.details;
				lecture.link = temp_lec.link;
				lecture.profId = temp_lec.profId;
				lecture.lectureId = temp_lec.lectureId;
				lecture.openAttendance = temp_lec.openAttendance;
				model.addAttribute("lecture", lecture);
				this.glob_courseCode = lecture.courseCode;
				this.glob_lecTitle = lecture.title;
				this.glob_profId = lecture.profId;
			}
		}
		ArrayList<ViewAttendance> attendees = new ArrayList<>();
		ArrayList<Attendance> findUsers = this.attendanceRepository.findByLectureId(this.glob_lectureId);
		Iterator<Attendance> a_cur = findUsers.iterator();
		while (a_cur.hasNext()) {
			Attendance temp_attend = a_cur.next();
			Person findName = this.personRepository.findById(temp_attend.studId);
			attendees.add(new ViewAttendance(temp_attend.studId, findName.username));

		}

		SeatingGenerator formatSeats = new SeatingGenerator();
		formatSeats.seatingList = this.seatingRepository.findByLectureIdOrderByColumn(this.glob_lectureId);
		formatSeats.displaySeats();
		model.addAttribute("seatingChart", formatSeats);
		model.addAttribute("attendance", attendees);

		return "prof/view_lecture";
	}

	/**
	 * In this function file will be uploaded to the lecture and be saved.
	 * 
	 * @param fileBucket
	 * @param result
	 * @param lecture
	 * @param person
	 * @param model
	 * @return prof/prof_home
	 * @throws IOException
	 */
	@PostMapping("/prof/view_lecture")
	public String view_lecture_post(Model model) throws IOException {
		
		return "prof/prof_home";
	}

	/**
	 * This function will let the professor to add assignments to according courses.
	 * They will select the course code first, then add the assignment name and the
	 * percentage of it.
	 * 
	 * @param person
	 * @param model
	 * @return prof/assignment
	 */
	@GetMapping("/prof/add_assignments")
	public String add_assignments(@CookieValue("person") String person, Model model) {
		Person prof = new Person();
		prof.parseStringData(person.split("===="));
		this.glob_profId = prof.id;

		// Retrieve list of courses the prof is enrolled in
		ArrayList<Enrollment> courses = new ArrayList<>();
		ArrayList<Enrollment> enrolled = enrollmentRepository.findAll();
		Iterator<Enrollment> e_cur = enrolled.iterator();
		while (e_cur.hasNext()) {
			Enrollment temp_prof = e_cur.next();
			if (prof.id == temp_prof.personId) {
				courses.add(temp_prof);
			}
		}

		// Add objects to view
		model.addAttribute("message", prof.name);
		model.addAttribute("courses", courses);

		Assignment assignment = new Assignment();
		model.addAttribute("assignments", assignment);
		model.addAttribute("message", "");

		return "prof/add_assignments";

	}

	/**
	 * This function will allow professor to post the assignment to the according
	 * course. It will also save the assignment to the repository.
	 * 
	 * @param assignment
	 * @param person
	 * @return prof/assignment
	 */
	@PostMapping("/prof/add_assignments")
	public String post_assignments(@ModelAttribute Assignment AssignmentName, @ModelAttribute Person person,
			BindingResult result, Model model) {
		try {
			AssignmentName.courseCode = glob_courseCode;
			System.out.println(AssignmentName.courseCode);
			System.out.println(AssignmentName);
			this.assignmentRepository.save(AssignmentName);
			this.glob_assName = AssignmentName.assName;
			return "redirect:/prof/course_page";
		} catch (Exception e) {
			model.addAttribute("message", "Error");
			return "redirect:/prof/course_page";
		}
	}

	/**
	 * This function will let professor grade the assignments after the students
	 * submit them.
	 * 
	 * @param model
	 * @return prof/grades
	 */
	@GetMapping("/prof/grades")
	public String add_grades(@CookieValue("person") String person, Model model) {
		Person prof = new Person();
		prof.parseStringData(person.split("===="));
		this.glob_profId = prof.id;
		
		// Retrieve list of courses the student is enrolled in
		this.glob_grades = new ArrayList<>();

		ArrayList<Enrollment> stud = new ArrayList<>();
		ArrayList<Enrollment> enrolled = this.enrollmentRepository.findByCourseCodeAndRole(this.glob_courseCode, "student");
		Iterator<Enrollment> e_cur = enrolled.iterator();
		while (e_cur.hasNext()) {
			Enrollment temp_stud = e_cur.next();
			this.glob_grades.add(new Grades(0, temp_stud.personId, 1, this.glob_assId));
		}
		
		model.addAttribute("grades", this.glob_grades);
		model.addAttribute("message", "");
		return "prof/grades";
	}

	/**
	 * This function will post the grades uploaded and calculate the total weightage
	 * according to the percentage set earlier.
	 * 
	 * @param grades
	 * @param assignment
	 * @param person
	 * @return prof/grades
	 */
	@PostMapping("/prof/grades")
	public String post_grades(@RequestParam("grade_students")String[] students) {
		Iterator<Grades> iter = this.glob_grades.iterator();
		int x = 0;
		while(iter.hasNext()) {
			Grades nextGrade = iter.next();
			nextGrade.grade = Integer.parseInt(students[x]);
			this.gradesRepository.save(nextGrade);
			x++;
		}
		return "redirect:/prof/course_page";

	}
 
     @GetMapping("/prof/uploadOneFile")
     public String listUploadedFiles(Model model) throws IOException {
    	 String fileName = glob_courseCode+"_";
    	 
    	 File folder = new File(this.rootLocation.toString());
    	 File[] listOfFiles = folder.listFiles();

    	     for (int i = 0; i < listOfFiles.length; i++) {
    	       if (listOfFiles[i].isFile()) {
    	    	   files.add(listOfFiles[i].getName());
    	         System.out.println("File " + listOfFiles[i].getName());
    	       } else if (listOfFiles[i].isDirectory()) {
    	         System.out.println("Directory " + listOfFiles[i].getName());
    	       }
    	     }
    	     
    	 model.addAttribute("files", files);
    	     
 		model.addAttribute("totalFiles", "TotalFiles: " + files.size());

         return "prof/uploadOneFile";
     }
     
    @GetMapping("/prof/{filename:.+}")
 	@ResponseBody
 	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    	 System.out.println(filename);
 		Resource file = storageService.loadFile(filename);
 		return ResponseEntity.ok()
 				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
 				.body(file);
 	}
     

     @PostMapping("/prof/uploadOneFile")
     public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
    	 try {
    		storageService.store(file);
 			model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
 			System.out.println("uploaded");
 			//files.add(file.getOriginalFilename());
 		} catch (Exception e) {
 			model.addAttribute("message", "FAIL to upload " + file.getOriginalFilename() + "!");
 		}
         return "redirect:/prof/course_page";
     }

}
