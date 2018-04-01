package whiteboard.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import whiteboard.login.PersonRepository;

@Controller
public class DocsController {
	@GetMapping("/docs")
	public String api_view() {
		return "docs";
	}
	
	@GetMapping("/docs/admin_features")
	public String admin_view() {
		return "docs/admin_features";
	}
	
	@GetMapping("/docs/db_info")
	public String db_view() {
		return "docs/db_info";
	}
	
	@GetMapping("/docs/login_features")
	public String login_view() {
		return "docs/login_features";
	}
	
	@GetMapping("/docs/prof_features")
	public String prof_view() {
		return "docs/prof_features";
	}
	
	@GetMapping("/docs/stud_features")
	public String stud_view() {
		return "docs/stud_features";
	}
}