package whiteboard.admin;

import org.springframework.data.repository.CrudRepository;

import whiteboard.login.*;

public interface AdminRepository extends CrudRepository<Person, Long>{

}
