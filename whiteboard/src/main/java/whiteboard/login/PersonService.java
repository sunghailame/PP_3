package whiteboard.login;


public interface PersonService {

	public Person findUserByUsername(String username);
	public void saveUser(Person person);
}
