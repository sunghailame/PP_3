
package whiteboard.location;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


public interface LocationRepository extends Repository<Location, Long> {

	void save(Location e);

	ArrayList<Location> findAll();

	Location findByLocationId(int buildingId);
}