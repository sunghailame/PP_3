package whiteboard.notification;

import java.sql.Date;
import java.util.Calendar;

public class NotificationGenerator {
	
	private Notification notification = new Notification();
	
	public NotificationGenerator(int receiver) {
		java.util.Date getCur = new java.util.Date();
    	notification.startDate = new java.sql.Date(getCur.getTime()); 
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_MONTH, 7);
    	notification.endDate = new java.sql.Date(cal.getTime().getTime());
    	notification.personId = receiver;
		
	}
	
	public Notification createNotification_newLecture(String courseCode, String title) {
		this.notification.courseCode = courseCode;
		String note = courseCode+"\nNew Lecture Created: \nTitle of Lecture - "+title; 
		this.notification.note = note.getBytes();
		return this.notification;
	}
	

	public Notification createNotification_newAssignment(String courseCode, String name) {
		this.notification.courseCode = courseCode;
		String note = courseCode+"\nNew Assignment Created: \nName of Assignment - "+name; 
		this.notification.note = note.getBytes();
		return this.notification;
	}
	
	
}
