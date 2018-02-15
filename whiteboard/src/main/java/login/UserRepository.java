package whiteboard;

import org.springframework.data.repository.CrudRepository;
import whiteboard.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
}
