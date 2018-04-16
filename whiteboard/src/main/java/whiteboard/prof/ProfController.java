package whiteboard.prof;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import whiteboard.course.Course;
import whiteboard.course.CourseRepository;
import whiteboard.document.Document;
import whiteboard.document.DocumentRepository;
import whiteboard.document.FileBucket;
import whiteboard.document.FileValidator;
import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
import whiteboard.grades.Assignment;
import whiteboard.grades.AssignmentRepository;
import whiteboard.grades.Grades;
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
import whiteboard.Message.Message;
import whiteboard.student.Attendance;
import whiteboard.student.AttendanceRepository;
import org.springframework.context.MessageSource;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

/**
 * A controller for professors. Professor normally has control over upload/viewing lecture, take/viewing attendance, view the courses that he is enrolled in, and post and grade assignments.
 * @author Sung Yealim
 *
 */
@Controller
public class ProfController {
	
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
	private DocumentRepository documentRepository;
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	FileValidator fileValidator;
	
	
	/**
	 * Function that binds the web data created.
	 * @param binder
	 */
	@InitBinder("fileBucket")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(fileValidator);
	}
	
	private int glob_profId;
	private int glob_lectureId;
	private String glob_courseCode;
	private String glob_lecTitle;
	private String glob_attendLec;
	private Date glob_Date;
	private String glob_Details;
	private String glob_Link;
	/**
	 * This function gets mapping from professor home. It shows the courses that the professor is currently enrolled in. 
	 * @param person: person object
	 * @param model: to add attributes to view
	 * @return prof/prof_home
	 */
    @GetMapping("/prof/prof_home")
    public String prof_home_get(@CookieValue("person") String person, Model model) {
    	//Parse Cookie into correct Person object
    	Person prof = new Person();
		prof.parseStringData(person.split("===="));
		this.glob_profId = prof.id;
		
		//Retrieve list of courses the prof is enrolled in
		ArrayList<Enrollment> courses = new ArrayList<>();
		ArrayList<Enrollment> enrolled = enrollmentRepository.findAll();
		Iterator<Enrollment> e_cur = enrolled.iterator();
		while(e_cur.hasNext()) {
			Enrollment temp_prof = e_cur.next();
			if(prof.id == temp_prof.personId) {
				courses.add(temp_prof);
			}
		}
		
		//Add objects to view
		model.addAttribute("message", prof.name);
		model.addAttribute("courses", courses);
		//model.addAttribute("link", "prof/course_page");
		model.addAttribute("person", person);
		model.addAttribute("linkToCourse", "prof/course_page");
		
        return "prof/prof_home";
    }
    /**
     * This function posts mapping to professor home. It will request parameter from professor's enrolled course to let the professor click on the course page he/she would want to view.
     * @param person: object
     * @param enroll_course: selected course
     * @param model
     * @return prof/course_page
     */
    @PostMapping("/prof/prof_home")
    public String prof_home_post(@ModelAttribute Person person, @RequestParam("c_enrolled") String enroll_course, Model model) {
    	//Get selected course from post 
    	Enrollment course = new Enrollment();
    	course.parseStringData(enroll_course.split("===="));
    	this.glob_courseCode = course.courseCode;
     	return "redirect:/prof/course_page";
    }   

     /**
      * This function gets mapping from course page. It gets the list of lectures by using the professor id and the course code that he/she is enrolled in. Then it will attach the lecture list to the view.
      * @param model
      * @return prof/course_page
      */
     @GetMapping("/prof/course_page")
     public String course_page_get(Model model) {
    	//Get list of lectures by courseCode and profId
    	 ArrayList<Lecture> lectures_temp = lectureRepository.findAll();
     	 Iterator<Lecture> lec_cur = lectures_temp.iterator();
     	 ArrayList<Lecture> lectures = new ArrayList<>();
     	 while(lec_cur.hasNext()) {
     		 Lecture lecture = (Lecture)lec_cur.next();
     		 if(this.glob_profId == lecture.profId && this.glob_courseCode.equals(lecture.courseCode)) {
     			 lectures.add(lecture);
     		 }
     	 }
     	 //Attach the lectureList to the view
     	 FormWrapper lectureList = new FormWrapper();
     	 lectureList.setLectures(lectures);
     	 model.addAttribute("lectures", lectureList);
     	 
     	 return "prof/course_page";
     }
     /**
      * This function posts mapping from course page. If the professor clicks on one of the course code that he/she would like to view, it will prompt him/her to the lecture page where they can view seating chart, attendance, and the grades.
      * @param person
      * @param view_lecture
      * @param model
      * @return prof/view_lecture
      */
     @PostMapping("/prof/course_page")
     public String course_page_post(@ModelAttribute Person person, @RequestParam("view_lecture") String[] view_lecture, Model model) {
    	
    	 for(int x=0; x < view_lecture.length; x++) {
    		 Lecture retLec = new Lecture();
    		 retLec.parseStringData(view_lecture[x].split("===="));
    		 if(view_lecture[x].contains("====viewThisOne")) {
    			 this.glob_lecTitle = retLec.title;
            	 this.glob_lectureId = retLec.lectureId;
    		 }
        	 if(view_lecture[x].contains("attendance") || view_lecture[x].contains("close")) {
        		 Lecture lec = lectureRepository.findByLectureId(retLec.lectureId);
        		 lec.setAttendance(retLec.openAttendance);
        		 lectureRepository.save(lec);
        	 } 
       }
    	 
 		return "redirect:/prof/view_lecture";
 	}
     /**
      * This function gets mapping from view location. It will find the course and locate the building assigned to it. 
      * @param model
      * @return prof/view_location
      */
     @GetMapping("/prof/view_location")
     public String view_location_get(Model model){
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
    	 model.addAttribute("course",this.glob_courseCode);
    	 //model.addAttribute("username", this.personRepository.findById(this.glob_profId).username);
    	 String username = this.personRepository.findById(this.glob_profId).username;
    	 //JSONObject usernameJson =usernameJson.fromObject(username);
    	 //String usernameStringJson = usernameJson.toString();
    	 model.addAttribute("username", username);  
    	 return "prof/chat";
     }
     
     @MessageMapping("/chat.sendMessage")
     @SendTo("/topic/public")
     public Message sendMessage(@Payload Message chatMessage) {
         return chatMessage;
     }

     @MessageMapping("/chat.addUser")
     @SendTo("/topic/public")
     public Message addUser(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
         // Add username in web socket session
         headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
         return chatMessage;
     }
     
     /**
      * This function gets mapping from new lecture. It will look for all the students who are enrolled to the according course and generate a seating chart for that lecture.
      * @param model
      * @return prof/new_lecture
      */
     @GetMapping("/prof/new_lecture")
     public String new_lecture_get(Model model) {
     	 Lecture new_lecture = new Lecture();
     	 SeatingGenerator seating = new SeatingGenerator();
     	 ArrayList<Enrollment> students = this.enrollmentRepository.findByCourseCodeAndRole(this.glob_courseCode,"student");
     	 ArrayList<Person> lookup = this.personRepository.findAll();
     	 seating.setView(students, lookup, 0);
     	 model.addAttribute("seating", seating);
     	 model.addAttribute("lecture", new_lecture);
    	 model.addAttribute("message", "");
     	 return "prof/new_lecture";
     }
     /**
      * This function posts mapping to new lecture. Professor would be able to view and assign students to the seating chart for the lecture as he wish.
      * @param person
      * @param lecture
      * @param seatingTable
      * @param model
      * @return prof/course_page
      */
     @PostMapping("/prof/new_lecture")
     public String new_lecture_post(@ModelAttribute Person person, @ModelAttribute Lecture lecture,  @RequestParam("seating_table") List<String> seatingTable, Model model) {
    	 lecture.lectureId = 0;
     	 lecture.profId = this.glob_profId;
     	 lecture.courseCode = this.glob_courseCode;
     	 lecture.openAttendance = false;
     	 java.util.Date getCur = new java.util.Date();
     	 lecture.lecDate = new java.sql.Date(getCur.getTime());
     	 this.lectureRepository.save(lecture);
     	 
     	 Lecture findId = this.lectureRepository.findByTitleAndLecDateAndProfId(lecture.title, lecture.lecDate, lecture.profId);
     	 SeatingGenerator seating = new SeatingGenerator();
     	 
     	 seating.assign(seatingTable, findId.lectureId);
     	 Iterator<SeatingChart> cur_seat = seating.seatingList.iterator();
     	 while(cur_seat.hasNext()) {
     		 SeatingChart add = cur_seat.next();
     		 this.seatingRepository.save(add);
     	 }
     	 
    	 model.addAttribute("message", "");
     	 return "redirect:/prof/course_page";
     }
    
     
     /**
      * In this function, professor will be able to view the lecture details and attendance for that lecture. It will also get the seating chart and the documents that the professor uploaded.
      * @param model
      * @return prof/view_lecture
      */
     @GetMapping("/prof/view_lecture")
     public String view_lecture_get(Model model) {
    	 Lecture lecture = new Lecture();
    	 ArrayList<Lecture> temp_lecture = lectureRepository.findAll();
    	 Iterator<Lecture> l_cur = temp_lecture.iterator();
    	 
    	 while(l_cur.hasNext()) {
    		 Lecture temp_lec = l_cur.next();
    		 //show the list of lectures
    		 if(temp_lec.courseCode.equals(this.glob_courseCode) && temp_lec.profId == this.glob_profId && 
    				 temp_lec.title.equals(this.glob_lecTitle)) {
    			 lecture.title = temp_lec.title;
    			 lecture.lecDate = temp_lec.lecDate;
    			 lecture.courseCode = temp_lec.courseCode;
    			 lecture.details = temp_lec.details;
    			 lecture.link = temp_lec.link;
    			 lecture.profId = temp_lec.profId;
    			 lecture.lectureId = temp_lec.lectureId;
    			 lecture.openAttendance = temp_lec.openAttendance;
    			 model.addAttribute("lecture",lecture);
    			 this.glob_courseCode = lecture.courseCode;
    			 this.glob_lecTitle = lecture.title;
    			 this.glob_profId = lecture.profId;
    		 }
    	 }
    	 ArrayList<ViewAttendance> attendees = new ArrayList<>();
    	 ArrayList<Attendance> findUsers = this.attendanceRepository.findByLectureId(this.glob_lectureId);
    	 Iterator<Attendance> a_cur = findUsers.iterator();
    	 while(a_cur.hasNext()) {
    		Attendance temp_attend = a_cur.next();
    		Person findName = this.personRepository.findById(temp_attend.studId);
    		attendees.add(new ViewAttendance(temp_attend.studId, findName.username));
    		 
    	 }
    	 
    	 SeatingGenerator formatSeats = new SeatingGenerator();
    	 formatSeats.seatingList = this.seatingRepository.findByLectureIdOrderByColumn(this.glob_lectureId);
    	 formatSeats.displaySeats();
    	 model.addAttribute("seatingChart", formatSeats);
    	 model.addAttribute("attendance", attendees);
    	 
    	
//    	 List<Document> documents = documentRepository.findAllByLectureId(lecture.lectureId);
//    	 for (Document d : documents) {
//    		 System.out.println(d.getName());
//    		 
//    	 }
//    	 model.addAttribute("documents", documents);
//    	 model.addAttribute("message","");
     	 return "prof/view_lecture";
     }
     /**
      * In this function file will be uploaded to the lecture and be saved.
      * @param fileBucket
      * @param result
      * @param lecture
      * @param person
      * @param model
      * @return prof/prof_home
      * @throws IOException
      */
     @PostMapping("/prof/view_lecture")
     public String view_lecture_post(@Valid FileBucket fileBucket, BindingResult result, @PathVariable Lecture lecture, @ModelAttribute Person person, Model model) throws IOException {
     	 if (result.hasErrors()) {
     		 System.out.println("validation errors");
     		 List<Document> documents = documentRepository.findAllByLectureId(lecture.lectureId);
     		 model.addAttribute("documents", documents);
     		 
     		
     		 
     	 } else {
     		 System.out.println("Fetching file");
     		 
     		 lecture = lectureRepository.findByLectureId(lecture.lectureId);
     		 model.addAttribute("lecture", lecture);
     		 saveDocument(fileBucket, lecture);
     	 }
    	 
    	 model.addAttribute("message", "");
     	 
     	 return "prof/prof_home";
     }
     /**
      * This function will let the professor to add assignments to according courses. They will select the course code first, then add the assignment name and the percentage of it.
      * @param person
      * @param model
      * @return prof/assignment
      */
     @GetMapping("/prof/add_assignments")
     public String add_assignments(@CookieValue("person") String person, Model model) {
    	Person prof = new Person();
 		prof.parseStringData(person.split("===="));
 		this.glob_profId = prof.id;
 		
 		//Retrieve list of courses the prof is enrolled in
 		ArrayList<Enrollment> courses = new ArrayList<>();
 		ArrayList<Enrollment> enrolled = enrollmentRepository.findAll();
 		Iterator<Enrollment> e_cur = enrolled.iterator();
 		while(e_cur.hasNext()) {
 			Enrollment temp_prof = e_cur.next();
 			if(prof.id == temp_prof.personId) {
 				courses.add(temp_prof);
 			}
 		}
 		
 		//Add objects to view
 		model.addAttribute("message", prof.name);
 		model.addAttribute("courses", courses);
 		
    	 Assignment assignment= new Assignment();
    	 model.addAttribute("assignments", assignment);
    	 model.addAttribute("message","");
    	 
    	 return "prof/add_assignments";

     }
     
     /**
      * This function will allow professor to post the assignment to the according course. It will also save the assignment to the repository.
      * @param assignment
      * @param person
      * @return prof/assignment
      */
     @PostMapping("/prof/add_assignments")
     public String post_assignments(@ModelAttribute Assignment AssignmentName, @ModelAttribute Person person, BindingResult result, Model model) {
    	 try{
    		 AssignmentName.courseCode = glob_courseCode;
    	 
    	 System.out.println(AssignmentName.courseCode);
    	 System.out.println(AssignmentName);
    	 this.assignmentRepository.save(AssignmentName);
    	 return "redirect:prof/course_page";
    	 }  catch (Exception e) {
    		 model.addAttribute("message", "Error");
    		 return "redirect:prof/course_page";
    	 }
    	 
     }
     /**
      * This function will let professor grade the assignments after the students submit them. 
      * @param model
      * @return prof/grades
      */
     @GetMapping("/prof/add_grades")
     public String add_grades(Model model) {
    	 return "prof/grades";
     }
     /**
      * This function will post the grades uploaded and calculate the total weightage according to the percentage set earlier.
      * @param grades
      * @param assignment
      * @param person
      * @return prof/grades
      */
     @PostMapping("/prof/add_grades")
     public String post_grades(@ModelAttribute Grades grades, @ModelAttribute Assignment assignment, @ModelAttribute Person person) {
    	 return "prof/grades";
     }
     
     /**
      * This function gets mapping from uploadOneFile. It will get the file that the professor uploaded.
      * @param model
      * @return prof/uploadOneFile
      */
     @GetMapping("/prof/uploadOneFile")
     public String uploadOneFile_get(Model model) {
    	 FileBucket fileModel = new FileBucket();
    	 model.addAttribute("fileBucket", fileModel);
    	 
    	 //List Documents here
    	 return "prof/uploadOneFile";
     }
     /**
      * This function posts mapping from uploadOneFile. If the professor would like to upload files for their lecture, it will be posted here.
      * @param model
      * @return prof/course_page
      */
     @PostMapping("/prof/uploadOneFile")
     public String uploadOneFile_post(@Valid FileBucket fileBucket, BindingResult result, Model model, @PathVariable int lectureId) throws IOException {
    	 if (result.hasErrors()) {
    		 System.out.println("validation errors");
    		 Lecture lec = lectureRepository.findByLectureId(lectureId);
    		 model.addAttribute("lecture", lec);
    		 
    		 //List Documents here
    		 return "/prof/uploadOneFile";
    	 } else {
    		 System.out.println("Fetching file");
    		 Lecture lec = lectureRepository.findByLectureId(lectureId);
    		 saveDocument(fileBucket, lec);
    	 }
    	 return "redirect:/prof/course_page";
     }
     /**
      * This function is used to save the documents that was uploaded by the professor.
      * @param fileBucket
      * @param lecture
      * @throws IOException
      */
     private void saveDocument(FileBucket fileBucket, Lecture lecture) throws IOException{
         
         Document document = new Document();
          
         MultipartFile multipartFile = fileBucket.getFile();
          
         document.setName(multipartFile.getOriginalFilename());
         document.setDescription(fileBucket.getDescription());
         document.setType(multipartFile.getContentType());
         document.setContent(multipartFile.getBytes());
         document.setLectureId(lecture.lectureId);
         documentRepository.save(document);
     }
     
     
     

     
     
}
