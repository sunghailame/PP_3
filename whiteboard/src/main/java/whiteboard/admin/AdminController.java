package whiteboard.admin;

import whiteboard.login.Person;
import whiteboard.login.PersonRepository;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AdminController {
	
	
	//Just a dummy list for testing purposes. Delete when repo added.
	public ArrayList<Person> dummyList(){
		
		ArrayList<Person> people = new ArrayList<Person>();
		
		for(int i=0;i< 10;i++) {
			people.add(new Person("username"+i, "password"+i, "name"+i, "email"+i, "role"+i, i));
		}
		
		return people;
	}
	
	
    @GetMapping("/admin/admin_home")
    public String signup_from_login(@CookieValue("person") String person, Model model) {
	  	System.out.println(person);
    	String[] dataSplit = person.split("=");
	  	Person admin = new Person();
	  	admin.id = Integer.parseInt(dataSplit[0]);
	  	admin.name = dataSplit[1];
	  	admin.username = dataSplit[2];
	  	admin.email = dataSplit[3];
	  	admin.role = dataSplit[4];
	  	admin.password = dataSplit[5];
    	model.addAttribute("username", admin.username);
    	model.addAttribute("name", admin.name);
    	
        return "admin/admin_home";
    }
    
     @PostMapping("/admin/admin_home")
    public String login_from_signup(@ModelAttribute Person person) {
        return "login/greeting";
    }
     
    
     @GetMapping("/admin/show_users")
     public String show_users_from_admin(Person person, Model model) {
    	 //TODO: Replace with repo findAll call
     	ArrayList<Person> allPeople = dummyList();
     	model.addAttribute("users", allPeople);
     	
         return "admin/show_users";
     }
}

