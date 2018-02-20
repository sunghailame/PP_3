package login;

import login.Person;

public interface PersonService {

	public Person findUserByUsername(String username);
	public void saveUser(Person person);
}
