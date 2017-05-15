package weather.boot;

import java.util.ArrayList;

public class UserPrefernce {

	private String userId;
	private ArrayList<String> cities;
	private int tempPrefernce;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ArrayList<String> getCities() {
		return cities;
	}
	public void setCities(ArrayList<String> cities) {
		this.cities = cities;
	}
	public int getTempPrefernce() {
		return tempPrefernce;
	}
	public void setTempPrefernce(int tempPrefernce) {
		this.tempPrefernce = tempPrefernce;
	}
	
	
	
}
