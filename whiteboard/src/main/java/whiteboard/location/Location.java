package whiteboard.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Location")
public class Location {
	
	@NotNull
	@Column(name = "LocationId")
	@Id
	public int locationId;
	
		@Column(name = "Building")
		public String building;
		
	
		@Column(name = "Latitude")
		public String latitude;
		
		@Column(name = "Longitude")
		public String longitude;
		
		public Location(int locationId, String building, String latitude, String longitude) {
			super();
			this.locationId = locationId;
			this.building = building;
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public Location() {
			
		}
		public int getLocationId() {
			return locationId;
		}

		public void setLocationId(int locationId) {
			this.locationId = locationId;
		}

		public String getBuilding() {
			return building;
		}

		public void setBuilding(String building) {
			this.building = building;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		@Override
		public String toString() {
			return this.building;
		}
	
		
		public String toStringData() {
			return this.locationId+"===="+this.building+"===="+this.latitude+"===="+this.longitude;
		}
		
		public void parseStringData(String[] dataSplit) {
			this.locationId = Integer.parseInt(dataSplit[0]);
			this.building = dataSplit[1];
			this.latitude = dataSplit[2];
			this.longitude = dataSplit[3];
		}
	}
