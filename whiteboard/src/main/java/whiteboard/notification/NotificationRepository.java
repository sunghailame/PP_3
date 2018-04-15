package whiteboard.notification;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends Repository<Notification, Long> {
	
	void save(Notification note);
	
	ArrayList<Notification> findByPersonId(int personId);
	
	void delete(Notification note);

}
