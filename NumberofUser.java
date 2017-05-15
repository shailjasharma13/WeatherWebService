package weather.boot;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class NumberofUser {
	@JsonSerialize
	public int numberofuser;

	public int getNumberofuser() {
		return numberofuser;
	}

	public void setNumberofuser(int numberofuser) {
		this.numberofuser = numberofuser;
	}
	

}
