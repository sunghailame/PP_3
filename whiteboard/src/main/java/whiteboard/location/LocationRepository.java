package whiteboard.location;

import java.util.ArrayList;
import org.springframework.data.repository.Repository;


public interface LocationRepository extends Repository<Location, Long> {

	/**
	 * Saves the location to the DB
	 * @param e
	 */
	void save(Location e);

	/**
	 * Returns the list of all locations in the DB as an array list of Location objects
	 * @return ArrayList<Location>
	 */
	ArrayList<Location> findAll();

	/**
	 * Returns the location corresponding to the buildingId
	 * @param buildingId
	 * @return Location
	 */
	Location findByLocationId(int buildingId);
}