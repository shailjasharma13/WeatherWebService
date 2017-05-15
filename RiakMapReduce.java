package weather.boot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.mapreduce.BucketMapReduce;
import com.basho.riak.client.api.commands.mapreduce.MapReduce;
import com.basho.riak.client.api.commands.mapreduce.MapReduce.Response;
import com.basho.riak.client.core.RiakFuture;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.functions.Function;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;

public class RiakMapReduce {

	public NumberofUser getNumberOfUser() {

		RiakClient rk = null;
		JsonNode ue = null;
		NumberofUser noOfuser=null;
		try {
			System.out.println("In number of User");
			rk = RiakClient.newClient(8087, "192.168.99.100");
			Namespace quotesBucket = new Namespace("user");

			// Function
			// reduceFunction=Function.newNamedJsFunction("Riak.mapValues");
			// Function
			// mapFunction=Function.newNamedJsFunction("Riak.mapValues");

			Function mapFunction = Function.newNamedJsFunction("function(v) {" + "var parse_data = v.values[0].data;"
					+ "var data={};" + "return [{'user_Id': (parse_data.userId)}];" +

					"}");

			Function reduceFunction = Function.newNamedJsFunction("function(v) {" + "var total = 0;"
					+ "for (var j in v) { " + " total += 1; }" + "return [{ " + "'numberofuser': total " + "}]; }");

			MapReduce mr = new BucketMapReduce.Builder().withNamespace(quotesBucket).withMapPhase(mapFunction, false)
					.withReducePhase(reduceFunction, true).build();

			ArrayNode usgd = rk.execute(mr).getResultsFromAllPhases();
			MapReduce.Response r = rk.execute(mr);

			System.out.println(r.getResultsFromAllPhases().size());

			for (JsonNode u : usgd) {
				System.out.println(u);
				ue = u;
				// User newJsonNode = mapper.treeToValue(u, User.class);
				// System.out.println(newJsonNode.getFirstName());
			}
			Gson gson = new Gson();
			noOfuser = gson.fromJson(ue.toString(), NumberofUser.class);
			rk.shutdown();

			// MapReduce mr = new
			// SearchMapReduce.Builder().withIndex("").withQuery(q).withMapPhase(Function.newNamedJsFunction("Riak.mapValues"));

		} catch (Exception e) {
			e.printStackTrace();
			rk.shutdown();
		}
		return noOfuser;
	}

	public static void main(String[] args) {
		System.out.println(new String("Shik").contains("k"));
	}
	public List<City> getCities(){
		RiakClient rk = null;
		JsonNode ue = null;
		List<City> cities=new ArrayList<City>();
		NumberofUser noOfuser=null;
		try {
			System.out.println("In number of User");
			rk = RiakClient.newClient(8087, "192.168.99.100");
			Namespace quotesBucket = new Namespace("city");

			// Function
			// reduceFunction=Function.newNamedJsFunction("Riak.mapValues");
			// Function
			// mapFunction=Function.newNamedJsFunction("Riak.mapValues");

			Function mapFunction = Function.newNamedJsFunction("function(v) { " + " var parse_data = JSON.parse(v.values[0].data); "
				+ "var data={};" + "var lat1 = 42.33; "+
				"var lon1=-83.05; "+
				"var lat2= parse_data.coord.lat; "+
				"var lon2=-parse_data.coord.lon; "+
				"var radlat1 = Math.PI * lat1/180; "+
	            "var radlat2 = Math.PI * lat2/180; "+
	            "var theta = lon1-lon2 ;"+
	            "var radtheta = Math.PI * theta/180; "+
	            "var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta); "+
	            "dist = Math.acos(dist); "+
	            "dist = dist * 180/Math.PI;"+
	            "if(dist<100){ "+
	            "return [{'dist': dist},{'city':parse_data.name}]; }else{return [0]}" +"}");

			Function reduceFunction = Function.newNamedJsFunction("function(v) {" + "var total = 0;"
					+ "for (var j in v) { " + " total += 1; }" + "return [{ " + "'numberofcities': total " + "}]; }");

			MapReduce mr = new BucketMapReduce.Builder().withNamespace(quotesBucket).withMapPhase(mapFunction, true)
					.withReducePhase(reduceFunction, true).build();

			ArrayNode usgd = rk.execute(mr).getResultsFromAllPhases();
			
			
			MapReduce.Response r = rk.execute(mr);
            System.out.println(usgd);
			System.out.println(r.getResultsFromAllPhases().size());

			for (JsonNode u : usgd) {
				
				if(u.toString().length()>3){
					if(u.toString().charAt(2)=='c')
					{
						String de = u.toString().replace("\\\"", "\"");
						String pe = de.replaceAll("\\\\", "");
						Gson gson = new Gson();
						City city=new City();
						city = gson.fromJson(pe, City.class);
						cities.add(city);
						
					}
				}
				
				
				// User newJsonNode = mapper.treeToValue(u, User.class);
				// System.out.println(newJsonNode.getFirstName());
			}
			
			System.out.println(cities);
			rk.shutdown();

			// MapReduce mr = new
			// SearchMapReduce.Builder().withIndex("").withQuery(q).withMapPhase(Function.newNamedJsFunction("Riak.mapValues"));

		} catch (Exception e) {
			e.printStackTrace();
			rk.shutdown();
		}
		
		return cities;
	}
	
}
