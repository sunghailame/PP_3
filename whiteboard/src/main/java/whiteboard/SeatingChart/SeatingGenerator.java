package whiteboard.SeatingChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import whiteboard.enrollment.Enrollment;
import whiteboard.login.Person;
import whiteboard.login.PersonRepository;

public class SeatingGenerator{
	
	public ArrayList<SeatingChart> seatingList;
	public List<String> stringList;
	public SeatingChart[] row_one;
	public SeatingChart[] row_two;
	public SeatingChart[] row_three;
	public SeatingChart[] row_four;
	
	public SeatingGenerator() {
		seatingList = new ArrayList<>();
	}
	
	public void setView(ArrayList<Enrollment> students, ArrayList<Person> lookupPeople, int lectureId) {
		Iterator<Enrollment> cur = students.iterator();
		HashMap<Integer, String> mapper = new HashMap<Integer, String>();
		for(Person person : lookupPeople) {
			mapper.put(person.id, person.name);
		}
		
		while(cur.hasNext()) {
			Enrollment stud = cur.next();
			// SeatingChart(int chartId, int lectureId, int studId, String studName, int x, int y)
			
			SeatingChart new_stud = new SeatingChart(0, lectureId, stud.personId, mapper.get(stud.personId), 0,0);
			seatingList.add(new_stud);
		}
	}
	
	public void assign(List<String> stringList, int lectureId) {
		Iterator<String> cur = stringList.iterator();
		while(cur.hasNext()) {
			String val = cur.next();
			if(!val.contains("Select student")) {
				SeatingChart stud = new SeatingChart();
				stud.parseStringData(val);
				stud.lectureId = lectureId;
				seatingList.add(stud);
			} else {
				int row = Integer.parseInt(val.substring(0,1));
				int column = Integer.parseInt(val.substring(2,3));
				SeatingChart stud = new SeatingChart(0, lectureId, 0, "", row, column);
				seatingList.add(stud);
			}
		}
	}
	
	public void displaySeats() {
		Iterator<SeatingChart> cur = this.seatingList.iterator();
		row_one = new SeatingChart[5];
		row_two = new SeatingChart[5];
		row_three = new SeatingChart[5];
		row_four = new SeatingChart[5];
		
		while(cur.hasNext()) {
			SeatingChart stud = cur.next();
			if(stud.row == 1) {
				row_one[stud.column] = stud;
			} else if(stud.row == 2) {
				row_two[stud.column] = stud;
			} else if(stud.row == 3) {
				row_three[stud.column] = stud;
			} else {
				row_four[stud.column] = stud;
			}
		}
	}
	
}