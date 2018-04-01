package whiteboard.prof;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;




import whiteboard.course.Course;
import whiteboard.enrollment.Enrollment;
import whiteboard.enrollment.EnrollmentRepository;
import whiteboard.lecture.Lecture;
import whiteboard.lecture.LectureRepository;
import whiteboard.login.Person;
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
			if(prof.id == temp_prof.person_id) {
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
    	this.glob_courseCode = course.course_code;
     	return "redirect:/prof/course_page";
    }   
 
     
     @GetMapping("/prof/course_page")
     public String course_page_get(Model model) {
    	//Get list of lectures by courseCode and profId
    	 ArrayList<Lecture> lectures_temp = lectureRepository.findAll();
     	 ArrayList<ViewLecture> lectures = new ArrayList<>();
     	 Iterator<Lecture> lec_cur = lectures_temp.iterator();
     	 while(lec_cur.hasNext()) {
     		 Lecture lecture = (Lecture)lec_cur.next();
     		 if(this.glob_profId == lecture.profId && this.glob_courseCode.equals(lecture.courseCode)) {
     			 ViewLecture l = new ViewLecture(lecture.title, lecture.lecDate, lecture.courseCode, false, lecture.profId, lecture.link, lecture.details, lecture.openAttendance);
     			 lectures.add(l);
     			 if(l.attendance == true) {
     				 this.glob_lecTitle = l.title;
     				 this.glob_Date = l.date;
     				this.glob_Details = l.details;
     				this.glob_Link = l.link;
     			 }
     		 }
     	 }
     	 
     	 //Attach the lectureList to the view
     	 FormWrapper lectureList = new FormWrapper();
     	 lectureList.setLectures(lectures);
     	 model.addAttribute("lectures", lectureList);
     	 return "prof/course_page";
     }
     
     @PostMapping("/prof/course_page")
     public String course_page_post(@ModelAttribute Person person, @RequestParam("view_lecture") String view_lecture, Model model) {
    	 
    	 System.out.println("post on course_page");
    	 System.out.println(view_lecture);
    	 Lecture retLec = new Lecture();
    	 
    	 //If attendance == "attendance", mark that, otherwise == "na" for view
    	 retLec.parseStringData(view_lecture.split("===="));

    	 this.glob_lecTitle = retLec.title;
    	 if(retLec.openAttendance || view_lecture.contains("attendance")) {
    		 Lecture lec = lectureRepository.findByTitleAndLecDateAndCourseCodeAndDetailsAndLinkAndProfId(retLec.title, retLec.lecDate, retLec.courseCode, retLec.details, retLec.link, retLec.profId);
    		 if(lec.openAttendance == false) {
    			 lec.setAttendance(true);
    		 } else {
    			 lec.setAttendance(false);
    		 }
    		 lectureRepository.save(lec);
    		 return "redirect:/prof/view_lecture";
    	 }
    	 
    	 System.out.println(retLec.toString());
    	 
 		return "redirect:/prof/view_lecture";
  
 	}
     
     @GetMapping("/prof/new_lecture")
     public String new_lecture_get(Model model) {
     	 Lecture new_lecture = new Lecture();
     	 model.addAttribute("lecture", new_lecture);
    	 model.addAttribute("message", "");
     	 return "prof/new_lecture";
     }
     @PostMapping("/prof/new_lecture")
     public String new_lecture_post(@ModelAttribute Person person, @ModelAttribute Lecture lecture, Model model) {
    	 lecture.lectureId = 0;
     	 lecture.profId = this.glob_profId;
     	 lecture.courseCode = this.glob_courseCode;
     	 java.util.Date getCur = new java.util.Date();
     	
     	 lecture.lecDate = new java.sql.Date(getCur.getTime());

     	 lecture.openAttendance = false;

     	 
     	 this.lectureRepository.save(lecture);
    	 model.addAttribute("message", "");
     	 return "redirect:/prof/course_page";
     }
     
     @GetMapping("/prof/view_lecture")
     public String view_lecture_get(Model model) {
    	 Lecture lecture = new Lecture();
    	 ArrayList<Lecture> temp_lecture = lectureRepository.findAll();
    	 Iterator<Lecture> l_cur = temp_lecture.iterator();
    	 ArrayList<Attendance> temp_attendance = attendanceRepository.findAll();
    	 ArrayList<Attendance> attendees = new ArrayList<>();
    	 Iterator<Attendance> a_cur = temp_attendance.iterator();
    	 
    	 while(l_cur.hasNext()) {
    		 Lecture temp_lec = l_cur.next();
    		 //show the list of lectures
    		 if(temp_lec.courseCode.equals(this.glob_courseCode) && temp_lec.profId == this.glob_profId && 
    				 temp_lec.title.equals(this.glob_lecTitle)) {
    			 System.out.println("Matching lecture?");
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
    	 while(a_cur.hasNext()) {
    		 Attendance temp_attend = a_cur.next();
    		 //show list of attendees
    		 if(temp_attend.lectureId == this.glob_lectureId) {
    			 Attendance attendance = new Attendance();
    			// attendance.CourseCode = temp_attend.CourseCode;
    			// attendance.date = temp_attend.date;
    			 attendance.ID = temp_attend.ID;
    			// attendance.SectionNo = temp_attend.SectionNo;
    			// attendance.lecture = temp_attend.lecture;
    			 attendance.studId = temp_attend.studId;
    			 attendance.lectureId = temp_attend.lectureId;
    			 attendees.add(attendance);
    		 }
    			 
    	 }
    	 model.addAttribute("attendance", attendees);

    	 //arraylist of attendance/people
    	 //model.add(arrayList)
    	 

    	 model.addAttribute("message","");
     	 return "prof/view_lecture";
     }
     @PostMapping("/prof/view_lecture")
     public String view_lecture_post(@ModelAttribute Person person, Model model) {
     	 model.addAttribute("message", "");
     	 return "prof/prof_home";
     }
     

     
     
}
