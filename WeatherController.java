package weather.boot;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

	@RequestMapping(value = "/nouser", method = RequestMethod.GET)
	public NumberofUser getNumberOfUser() {
		System.out.println("inside no user");
		NumberofUser numberofUser = new RiakMapReduce().getNumberOfUser();
		System.out.println("Result :: " + numberofUser.getNumberofuser());
		return numberofUser;
	}
	
	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public List<City> getCities() {
		System.out.println("inside cities");
		List<City> cities = new RiakMapReduce().getCities();
		System.out.println("Result :: " + cities);
		return cities;
	}

	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	public void user(@RequestBody User user) {
		System.out.println(user.getUserId() + "/login   " + user.getFirstName() + "  " + user.getCityList());
		new RiakDAO().updateUser(user);

	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public User getUserById(@PathVariable String userId) {
		System.out.println("In getUserbyId");
		User user = new RiakDAO().getUser(userId);
		return user;
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public User userLogin(@RequestBody User user) {
		System.out.println("In getUserLogin" + user.getUserId());
		Boolean result = new RiakDAO().UserLogin(user);
		System.out.println("Rseult" + result);
		if (user != null & result) {
			System.out.println("woow");
			user = getUserById(user.getUserId());
		}
		System.out.println("kljhkjgfthk" + result);
		return user;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		List<User> users = new RiakDAO().getAllUsers();
		return users;
	}

	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User addUser(@RequestBody User user) {

		// Is this user Exists ?
		// getUserById(user.getUserId())
		System.out.println("In add User::" + user.getUserId());
		if (getUserById(user.getUserId()) == null) {
			System.out.println("In addUser");
			System.out.println("User id - " + user.getUserId());
			String result = new RiakDAO().addUser(user);
			System.out.println("Result of User Creation :" + result);
			return getUserById(user.getUserId());
		}
		return user;
	}

	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
	public void deleteUserById(@PathVariable String userId) {
		System.out.println("In delete user");
		String result = new RiakDAO().deleteUser(userId);
		System.out.println(result);
	}
}
	/*
	 * @RequestMapping(value="/users/update", method=RequestMethod.PUT) public
	 * void updateUser(@RequestBody User user) { String updateduser=new
	 * RiakDAO().updateUser(user); System.out.println(updateduser); }
	 */


	/*
	 * @RequestMapping(value="user/getCityList", method=RequestMethod.GET)
	 * public User getCityByUserId(@RequestParam String userId) {
	 * System.out.println("UserId"+userId); User user=new
	 * RiakDAO().getUser(userId); System.out.println(user.getCityList()); return
	 * user; }
	 * 
	 * @RequestMapping(value="/user/{userId}/city", method=RequestMethod.GET)
	 * public String getAllCityByUserId(@RequestParam String
	 * userId,@RequestParam String password) { String result=new
	 * RiakDAO().deleteUser(userId); return result; }
	 * 
	 * @RequestMapping(value="/user/{userId}/city", method=RequestMethod.POST)
	 * public String addCityByUserId(@PathVariable String userId,@RequestParam
	 * String password) { String result=new RiakDAO().deleteUser(userId); return
	 * result; }
	 * 
	 * @RequestMapping(value="/user/{userId}/city/{cityId}",
	 * method=RequestMethod.DELETE) public String
	 * deleteCityByUserId(@RequestParam String userId,@RequestParam String
	 * password) { String result=new RiakDAO().deleteUser(userId); return
	 * result; }
	 */

	/*
	 * @RequestMapping(value="/getallUser", method=RequestMethod.GET) public
	 * String getAlluser() { String result="gjhggjh"; return result; }
	 * 
	 * @RequestMapping("/user/weather" ) public String
	 * userWeather(@RequestParam(value="city",defaultValue="Detroit") String
	 * city) { System.out.println("In user weather"); String weatherString=null;
	 * try { weatherString = ReadWeather.readJsonFromUrl(
	 * "http://api.openweathermap.org/data/2.5/weather?q="+city+
	 * "&appid=29d14bfdfe923c4130e25e63c0f9600e"); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } return
	 * weatherString; }
	 * 
	 * @RequestMapping(value="/signup", method=RequestMethod.POST) public String
	 * SignUp(@RequestParam String firstName,@RequestParam String
	 * lastName,@RequestParam String userId,@RequestParam String password) {
	 * User userInfo=new User(); userInfo.setFirstName(firstName);
	 * userInfo.setLastName(lastName); userInfo.setUserId(userId);
	 * userInfo.setPassword(password); String result=new
	 * RiakDAO().addUser(userInfo); return result; }
	 * 
	 * @RequestMapping(value="/user/delete", method=RequestMethod.DELETE) public
	 * String DeleteUser(@RequestParam String userId,@RequestParam String
	 * password) { String result=new RiakDAO().deleteUser(userId); return
	 * result; }
	 * 
	 * 
	 * @RequestMapping(value="/user/userPreference", method=RequestMethod.GET)
	 * public String setUserPrefernce(@RequestParam String userId,@RequestParam
	 * String password) { String result=new RiakDAO().deleteUser(userId); return
	 * result; }
	 */

	// ------------------------------------------------------------

