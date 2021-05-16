package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	List<Junction> junctions;
	List<Road> roads;
	List<Vehicle> vehicles;
	Map<String,Junction> juncmap;
	Map<String,Road> roadmap;
	Map<String,Vehicle> vehiclemap;
	
	RoadMap()
	{
		this.junctions = new ArrayList<>();
		this.roads = new ArrayList<>();
		this.vehicles = new ArrayList<>();
		this.juncmap = new HashMap<>();
		this.roadmap = new HashMap<>();
		this.vehiclemap = new HashMap<>();
	}
	
	public void addJunction(Junction j)
	{
		if(!juncmap.containsKey(j.getId()))
		{
			junctions.add(j);
			juncmap.put(j.getId(), j);
		}
		else throw new IllegalArgumentException("This junction is already on the map");
	}
	
	public void addRoad(Road r)
	{
		if(!roadmap.containsKey(r.getId()) && juncmap.containsValue(r.getDest()) && juncmap.containsValue(r.getSrc()))
		{
			roads.add(r);
			r.getDest().addIncomingRoad(r);
			r.getSrc().addOutgoingRoad(r);
			roadmap.put(r.getId(), r);
		}
		else throw new IllegalArgumentException("Can´t add the road to the map");
	}

	public void addVehicle(Vehicle v)
	{
		int i = 1;
		Junction j;
		Junction j2;
		
		while(i<v.getItinerary().size())
		{
			j = v.getItinerary().get(i - 1);
			j2 = v.getItinerary().get(i);
			if(!roadmap.containsValue(j.roadTo(j2))) throw new IllegalArgumentException("Can´t add this vehicle");
			i++;
			
		}
		
		if(!vehiclemap.containsKey(v.getId()))
		{
			vehicles.add(v);
			vehiclemap.put(v.getId(),v);
		}
		else throw new IllegalArgumentException();
	}
	
	public Vehicle getVehicle(String s)
	{
		
		if(vehiclemap.containsKey(s)) return vehiclemap.get(s);
		else return null;
	}
	
	public List<Vehicle> getVehicles()
	{
		List<Vehicle> vlist = vehicles;
		return vlist;
	}
	
	public Road getRoad(String s)
	{
		if(roadmap.containsKey(s)) return roadmap.get(s);
		else return null;
	}
	
	public List<Road> getRoads()
	{
		List<Road> rlist = roads;
		return rlist;
	}
	
	public Junction getJunction(String s)
	{
		if(juncmap.containsKey(s)) return juncmap.get(s);
		else return null;
	}
	
	public List<Junction> getJunctions()
	{
		List<Junction> jlist = junctions;
		return jlist;
	}
	
	void reset()
	{
		new RoadMap();
	}
	
	public JSONObject report()
	{
		JSONObject j = new JSONObject();
		JSONArray ja = new JSONArray();
		
		for(Junction junc : junctions)
		{
			ja.put(junc.report());
		}
		
		j.put("junctions", ja);
		ja = new JSONArray();
		
		for(Road r : roads)
		{
			ja.put(r.report());
			
		}
		
		j.put("roads", ja);
		ja = new JSONArray();
		
		for(Vehicle v : vehicles)
		{
			ja.put(v.report());
		}
		
		j.put("vehicles" , ja);
		
		return j;
	}
}
