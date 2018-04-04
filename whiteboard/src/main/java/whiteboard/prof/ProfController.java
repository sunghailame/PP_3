package whiteboard.prof;

import java.sql.Date;
import whiteboard.student.ViewAttendance;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import whiteboard.course.Course;
import whiteboard.course.CourseRepository;
import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
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
import whiteboard.student.Attendance;
import whiteboard.student.AttendanceRepository;

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
	
	private int glob_profId;
	private int glob_lectureId;
	private String glob_courseCode;
	private String glob_lecTitle;
	private String glob_attendLec;
	private Date glob_Date;
	private String glob_Details;
	private String glob_Link;
	
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
    
    @PostMapping("/prof/prof_home")
    public String prof_home_post(@ModelAttribute Person person, @RequestParam("c_enrolled") String enroll_course, Model model) {
    	//Get selected course from post 
    	Enrollment course = new Enrollment();
    	course.parseStringData(enroll_course.split("===="));
    	this.glob_courseCode = course.courseCode;
     	return "redirect:/prof/course_page";
    }   
 
     
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
     
     @PostMapping("/prof/course_page")
     public String course_page_post(@ModelAttribute Person person, @RequestParam("view_lecture") String[] view_lecture, Model model) {
    	
    	 
    	 for(int x=0; x < view_lecture.length; x++) {
    		 Lecture retLec = new Lecture();
    		 retLec.parseStringData(view_lecture[x].split("===="));
    		 this.glob_lecTitle = retLec.title;
        	 this.glob_lectureId = retLec.lectureId;
        	 if(view_lecture[x].contains("attendance") || view_lecture[x].contains("close")) {
        		 Lecture lec = lectureRepository.findByLectureId(retLec.lectureId);
        		 lec.setAttendance(retLec.openAttendance);
        		 lectureRepository.save(lec);
        	 } 
       }
    	 
 		return "redirect:/prof/view_lecture";
  
 	}
     
     @GetMapping("/prof/view_location")
     public String view_location_get(Model model){
    	 Course findId = this.courseRepository.findByCourseCode(this.glob_courseCode);
    	 Location building = this.locationRepository.findByLocationId(findId.buildingId);
    	 LocationGenerator gen = new LocationGenerator();
    	 String mapView = gen.embedLink(building.latitude, building.longitude);
    	 model.addAttribute("link", mapView);
    	 return "prof/view_location";
     }
     
     
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
     
     @GetMapping("/prof/view_lecture")
     public String view_lecture_get(Model model) {
    	 Lecture lecture = new Lecture();
    	 ArrayList<Lecture> temp_lecture = lectureRepository.findAll();
    	 Iterator<Lecture> l_cur = temp_lecture.iterator();
    	 ArrayList<Attendance> temp_attendance = attendanceRepository.findAll();
    	 Iterator<Attendance> a_cur = temp_attendance.iterator();
    	 
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
    			 lecture.lectureId = 0;
    			 lecture.openAttendance = temp_lec.openAttendance;
    			 model.addAttribute("lecture",lecture);
    			 this.glob_courseCode = lecture.courseCode;
    			 this.glob_lecTitle = lecture.title;
    			 this.glob_profId = lecture.profId;
    		 }
    	 }
    	 ArrayList<ViewAttendance> attendees = new ArrayList<>();
    	 while(a_cur.hasNext()) {
    		 Attendance temp_attend = a_cur.next();
    		 //show list of attendees
    		 if(temp_attend.lectureId == this.glob_lectureId) {
    			 Person stud = this.personRepository.findById(temp_attend.studId);
    			 attendees.add(new ViewAttendance(stud.id, stud.name));
    		 }
    	 }
    	 SeatingGenerator formatSeats = new SeatingGenerator();
    	 formatSeats.seatingList = this.seatingRepository.findByLectureIdOrderByColumn(this.glob_lectureId);
    	 formatSeats.displaySeats();
    	 model.addAttribute("seatingChart", formatSeats);
    	 model.addAttribute("attendance", attendees);

    	 model.addAttribute("message","");
     	 return "prof/view_lecture";
     }
     @PostMapping("/prof/view_lecture")
     public String view_lecture_post(@ModelAttribute Person person, Model model) {
     	 model.addAttribute("message", "");
     	 return "prof/prof_home";
     }
     

     
     
}
