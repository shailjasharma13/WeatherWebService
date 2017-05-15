package weather.boot;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.DeleteValue;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.ListKeys;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.api.commands.kv.UpdateValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.google.gson.Gson;
 


public class RiakDAO {

	public String addUser(User user) {
		RiakClient client=null;
		try {

			client = getRiakClient();
			System.out.println("Made Connection in Add User ");
			Namespace quotesBucket = new Namespace("user");
			Location quoteObjectLocation = new Location(quotesBucket, user.getUserId());
			Gson gson = new Gson();
			StoreValue storeWeather = new StoreValue.Builder(gson.toJson(user)).withLocation(quoteObjectLocation)
					.build();
			client.execute(storeWeather);
			client.shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
			client.shutdown();
		}
		return "success";
	}

	public Boolean UserLogin(User userLogin) {
		RiakClient client=null;
		User user =null;
		try {

			client = getRiakClient();
			System.out.println("Made Connection in User Login");
			Namespace quotesBucket = new Namespace("user");
			if (userLogin != null) 
			{
				String userId = userLogin.getUserId();
				String password = userLogin.getPassword();
				Location quoteObjectLocation = new Location(quotesBucket, userId);
				FetchValue fetchOp = new FetchValue.Builder(quoteObjectLocation).build();
				RiakObject fetchedObject = client.execute(fetchOp).getValue(RiakObject.class);
				Gson gson = new Gson();
				if(fetchedObject!=null){
			    user = gson.fromJson(fetchedObject.getValue().toString(), User.class);}
				
				if(user == null) return false;
				client.shutdown();
				if (user.getPassword().equals(password)) 
				{
					return new Boolean(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			client.shutdown();
		}
		return new Boolean(false);
	}

	public String deleteUser(String userId) {
		
		RiakClient client=null;
		try {

			client =  getRiakClient();;
			System.out.println("Made Connection in delete User");
			Namespace quotesBucket = new Namespace("user");
			Location quoteObjectLocation = new Location(quotesBucket, userId);
			FetchValue fetchOp = new FetchValue.Builder(quoteObjectLocation).build();
			RiakObject fetchedObject = client.execute(fetchOp).getValue(RiakObject.class);
			Gson gson = new Gson();
			User user = gson.fromJson(fetchedObject.getValue().toString(), User.class);
			if (user.getUserId().equals(userId)) {
				DeleteValue deleteOp = new DeleteValue.Builder(quoteObjectLocation).build();
				client.execute(deleteOp);
			}
			client.shutdown();

		} catch (Exception e) {
			e.printStackTrace();
			client.shutdown();
		}
		return "UserId Deleted";
	}

	public User getUser(String userId) {
		User user = null;
		RiakClient client=null;
		try {

			client =  getRiakClient();
			System.out.println("Made Connection in GetUser");
			Namespace quotesBucket = new Namespace("user");
			Location quoteObjectLocation = new Location(quotesBucket, userId);
			FetchValue fetchOp = new FetchValue.Builder(quoteObjectLocation).build();
			RiakObject fetchedObject = client.execute(fetchOp).getValue(RiakObject.class);
			// System.out.println("fetched:" + fetchedObject.getValue());
			Gson gson = new Gson();

			if (fetchedObject != null) {
				user = gson.fromJson(fetchedObject.getValue().toString(), User.class);
			}
			client.shutdown();

		} catch (Exception e) {
			e.printStackTrace();
			client.shutdown();
		}
		return user;

	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			List<String> keys = getAllKeys();

			for (String key : keys) {
				users.add(getUser(key));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}

	public String updateUser(User user) {
		RiakClient client=null;
		try {

			client = getRiakClient();
			System.out.println("Made Connection in update User");
			Namespace quotesBucket = new Namespace("user");
			Location quoteObjectLocation = new Location(quotesBucket, user.getUserId());
			UserUpdate userUpdate=new UserUpdate(user);
			Gson gson = new Gson();
			UpdateValue  storeWeather = new UpdateValue .Builder(quoteObjectLocation).withUpdate(userUpdate)
					.build();
			client.execute(storeWeather);
			client.shutdown();



		} catch (Exception e) {
			e.printStackTrace();
			client.shutdown();
		}
		return "update Successful";

	}

	private RiakClient getRiakClient()  {
		RiakClient client =null;
		try{
		 client = RiakClient.newClient(8087, "192.168.99.100");
		return client;
		}catch(Exception e){
			e.printStackTrace();
			client.shutdown();
		}
		return client;
	}

	private List<String> getAllKeys() {
		RiakClient client=null;
		List<String> keys=null;
		try{
		keys = new ArrayList<String>();
		client = getRiakClient();
		System.out.println("Made Connection in get All Keys");
		Namespace quotesBucket = new Namespace("user");
		ListKeys lk = new ListKeys.Builder(quotesBucket).build();
		ListKeys.Response response = client.execute(lk);
		for (Location l : response) {
			System.out.println(l.getKeyAsString());
			keys.add(l.getKeyAsString());
		}
		client.shutdown();
		return keys;
		}
		catch(Exception e){
			e.printStackTrace();
			client.shutdown();
		}
		return keys;
	}

}