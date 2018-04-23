package whiteboard.student;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
import whiteboard.Message.Message;
import whiteboard.SeatingChart.SeatingChartRepository;
import whiteboard.SeatingChart.SeatingGenerator;
import whiteboard.admin.EnrollCourse;
import whiteboard.admin.FormWrapper;
import whiteboard.login.PersonRepository;
import whiteboard.notification.Notification;
import whiteboard.notification.NotificationGenerator;
import whiteboard.notification.NotificationRepository;
import whiteboard.prof.ViewLecture;
import whiteboard.storage.StorageService;
/**
 * A controller for students. A student views lectures, they take attendance, view the seating chart and Submit assignment and view grades
 * @author Mireille Mwiza Iradukunda
 *
 */
@Controller
public class StudentController {
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private LectureRepository lectureRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private SeatingChartRepository seatingRepository;
	
	@Autowired 
	private CourseRepository courseRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private GradesRepository gradesRepository;

	private final StorageService storageService;
	
	List<String> files = new ArrayList<String>();
	private final Path rootLocation = Paths.get("upload-dir");
	
	private int glob_profId;
	private String glob_courseCode;
	private String glob_lecTitle;
	private int glob_lecId;
	private int glob_studId;
	private int glob_assId;
	
	
	@Autowired
	public StudentController(StorageService storageService) {
		this.storageService = storageService;
	}
	/**
	 * This function gets mapping from student home. It shows the courses that the student is currently enrolled in. 
	 * @param person: person object
	 * @param model: to add attributes to view
	 * @return student/student_home
	 */
    @GetMapping("/student/student_home")
    public String stud_home_get(@CookieValue("person") String person, Model model) {
    	//Parse Cookie into correct Person object
    	Person stud = new Person();
		stud.parseStringData(person.split("===="));
		this.glob_studId = stud.id;
		
		//Retrieve list of courses the prof is enrolled in
		ArrayList<Enrollment> courses = new ArrayList<>();
		ArrayList<Enrollment> enrolled = enrollmentRepository.findAll();
		Iterator<Enrollment> e_cur = enrolled.iterator();
		while(e_cur.hasNext()) {
			Enrollment temp_prof = e_cur.next();
			if(stud.id == temp_prof.personId) {
				courses.add(temp_prof);
			}
		}
		//Retrieve list of notifications
		ArrayList<Notification> nlist = new ArrayList<Notification>();
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		nlist = notificationRepository.findByPersonId(this.glob_studId);
		java.util.Date getCur = new java.util.Date();
    	 Date today_date = new java.sql.Date(getCur.getTime());
		for (Notification n : nlist) {
			if (n.getEndDate().before(today_date) ) {
				notificationRepository.delete(n);
			} else {
				notifications.add(n);
			}
		}
		ArrayList<String> notes = new ArrayList<String>();
		for (Notification not: notifications) {
			notes.add(new String(not.note));
		}
		
		
		//Add objects to view
		model.addAttribute("message", "");
		model.addAttribute("courses", courses);
		model.addAttribute("person", person);
		model.addAttribute("notifications", notes );
		
		
        return "student/student_home";
    }
    /**
     * This function posts mapping to student home. It will request parameter from student's enrolled course to let the student click on the course page he/she would want to view.
     * @param person: object
     * @param enroll_course: selected course
     * @param model
     * @return student/course_page
     */
    @PostMapping("/student/student_home")
    public String stud_home_post(@ModelAttribute Person person, @RequestParam("c_enrolled") String enroll_course, Model model) {
    	//Get selected course from post 
    	Enrollment course = new Enrollment();
    	course.parseStringData(enroll_course.split("===="));
    	this.glob_courseCode = course.courseCode;
     	return "redirect:/student/course_page";
    }
     
    /**
     * This function gets mapping from course page. It gets the list of lectures by using the student id and the course code that he/she is enrolled in. Then it will attach the lecture list to the view.
     * @param model
     * @return student/course_page
     */
     @GetMapping("/student/course_page")
     public String course_page_get(Model model) {
    	//Get list of lectures by courseCode and profId
    	 ArrayList<Lecture> lectures_temp = lectureRepository.findAll();
     	 ArrayList<Lecture> lectures = new ArrayList<>();
     	 Iterator<Lecture> lec_cur = lectures_temp.iterator();
     	 
     	 ArrayList<Grades> grades_temp = gradesRepository.findAll();
     	 Iterator<Grades> grades_cur = grades_temp.iterator();
     	 ArrayList<Grades> grades = new ArrayList<>();
     	 
     	 while(lec_cur.hasNext()) {
     		 Lecture lecture = (Lecture)lec_cur.next();
     		 if(this.glob_courseCode.equals(lecture.courseCode)) {
     			 lectures.add(lecture);
     		 }
     	 }
     	 
     	 //Attach the lectureList to the view
     	 FormWrapper lectureList = new FormWrapper();
     	 lectureList.setLectures(lectures);
     	 model.addAttribute("lectures", lectureList);
     	 
     	 ArrayList<Grades> gradesList = this.gradesRepository.findByStudentId(glob_studId);
     	 model.addAttribute("grades", gradesList);
     	 
     	 return "student/course_page";

     }
     /**
      * This function posts mapping to course page. It will request parameter from student's view lecture to let the student click on the lecture page he/she would want to view.
      * @param person: object
      * @param view_lecture: view lecture
      * @param model
      * @return student/view_lecture
      */
     @PostMapping("/student/course_page")

     public String course_page_post(@ModelAttribute Person person, @RequestParam("view_lecture") String view_lecture, Model model) {
    	 Lecture retLec = new Lecture();
    	 retLec.parseStringData(view_lecture.split("===="));
    	 this.glob_lecTitle = retLec.title;
    	 this.glob_lecId = retLec.lectureId;

 		return "redirect:/student/view_lecture";

 	}
     /**
 	 * This function gets mapping from student view lecture page. it views students seating for the students 
 	 * @param person: person object
 	 * @param model: to add attributes to view
 	 * @return student/view_lecture
 	 */
     @GetMapping("/student/view_lecture")
     public String view_lecture_get(Model model) throws Exception {
    	 Lecture lecture = new Lecture();
    	 int lecId; 
    	 lecture = this.lectureRepository.findByLectureId(this.glob_lecId);
    	 
    	 model.addAttribute("lecture", lecture);
    	 model.addAttribute("message","");
    	 
    	 Assignment assignment = new Assignment();
    	 int assId;
    	 
    	 assignment = this.assignmentRepository.findByAssId(this.glob_assId);
    	 
    	 //Student can view files uploaded
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

    	 
    	 model.addAttribute("assignment", assignment);
    	 model.addAttribute("message", "");
    	 
    	 
    	 try {
 			SeatingGenerator formatSeats = new SeatingGenerator();
 			formatSeats.seatingList = this.seatingRepository.findByLectureIdOrderByColumn(this.glob_lecId);
 			if(formatSeats.seatingList.isEmpty()) {
 				List<String> seatNulls = new ArrayList<>();
 				for(int x = 1; x < 6; x++) {
 					for(int y = 0; y < 5; y++) {
 						seatNulls.add(x+","+y+"Select student");
 					}
 				}
 				formatSeats.assignNulls(seatNulls, this.glob_lecId);
 				formatSeats.displaySeats();
 				model.addAttribute("seatingChart", formatSeats);
 			} else {
 				formatSeats.displaySeats();
 				model.addAttribute("seatingChart", formatSeats);
 			}
 			
 			return "student/view_lecture";
 		} catch (Exception e) {
 			SeatingGenerator formatSeats = new SeatingGenerator();
 			List<String> seatNulls = new ArrayList<>();
 			for(int x = 1; x < 6; x++) {
 				for(int y = 0; y < 5; y++) {
 					seatNulls.add(x+","+y+"Select student");
 				}
 			}
 			formatSeats.assignNulls(seatNulls, this.glob_lecId);
 			formatSeats.displaySeats();
 			model.addAttribute("seatingChart", formatSeats);
 			return "student/view_lecture";
 		}

     }
     
     @GetMapping("/student/{filename:.+}")
  	@ResponseBody
  	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
     	 System.out.println(filename);
  		Resource file = storageService.loadFile(filename);
  		return ResponseEntity.ok()
  				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
  				.body(file);
  	}
     /**
      * This function posts mapping from student view lecture page. If the student clicks on one of the course code that he/she would like to view, it will prompt him/her to the lecture page where they can view seating chart, take attendance, and the grades.
      * @param person
      * @param view_lecture
      * @param model
      * @return student/view_lecture
      */
     @PostMapping("/student/view_lecture")
     public String view_lecture_post(@ModelAttribute Person person, @RequestParam("attendance") String attendance, Model model) {
     	System.out.println(attendance);
    	Lecture retLec = new Lecture();
    	Assignment retAss = new Assignment();
     	retLec.parseStringData(attendance.split("===="));
     	
     	Attendance checkDouble = attendanceRepository.findByLectureIdAndStudId(retLec.lectureId, glob_studId);
     	ArrayList<Assignment> posted = this.assignmentRepository.findByCourseCode(retAss.courseCode);
     	
     	if(checkDouble == null) {
    	if(retLec.openAttendance) {
    		int lecId = retLec.lectureId;
    		this.attendanceRepository.save(new Attendance(0, glob_studId, lecId));
    	}
     	}
    	 
    	 model.addAttribute("message", "");
     	 return "redirect:/student/course_page";

     }
     /**
      * This function gets mapping from view location. It will find the course and locate the building assigned to it. 
      * @param model
      * @return prof/view_location
      */
     @GetMapping("/student/view_location")
     public String view_location_get(Model model){
    	 Course findId = this.courseRepository.findByCourseCode(this.glob_courseCode);
    	 Location building = this.locationRepository.findByLocationId(findId.buildingId);
    	 LocationGenerator gen = new LocationGenerator();
    	 String mapView = gen.embedLink(building.latitude, building.longitude);
    	 model.addAttribute("link", mapView);
    	 model.addAttribute("building", building.building);
    	 return "student/view_location";
     }
     
     @GetMapping("student/chat")
     public String chat_get(Model model) {
    	 model.addAttribute("course",this.glob_courseCode);
    	 String username = this.personRepository.findById(this.glob_studId).username;
    	 model.addAttribute("username", username);  
    	 model.addAttribute("courseCode",this.glob_courseCode);
    	 model.addAttribute("role", "student");
    	 return "student/chat";
     }
     
}
