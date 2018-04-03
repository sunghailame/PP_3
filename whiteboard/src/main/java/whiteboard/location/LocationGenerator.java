package whiteboard.location;

import java.util.ArrayList;

public class LocationGenerator{
	//API key: AIzaSyCftSpbCu6tTTFluzmAhwWBQopQRiVhfVE
	
	public String api_key = "AIzaSyCftSpbCu6tTTFluzmAhwWBQopQRiVhfVE";
	public String google_embed = "https://www.google.com/maps/embed/v1/place?key=";
	public String place_start = "&q=";
	
	public ArrayList<Location> locations;
	
	public LocationGenerator() {
		locations = new ArrayList<>();
	}
	
	public String embedLink(String latitude, String longitude) {
		return google_embed+api_key+place_start+latitude+','+longitude;
	}
	
}