package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject  {

	private List<Road> roads;
	private Map<Junction, Road> map;
	private List<List<Vehicle>> vehiclelist;
	private Map<Road,List<Vehicle>> maproads;
	private int tlight;
	private int lastlight;
	private LightSwitchingStrategy lightStrat;
	private DequeuingStrategy deqStrat;
	private int x;
	private int y;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy deqStrat,
			int x, int y) {
		super(id);
		
		if(lsStrategy != null && deqStrat != null)
		{
			this.lightStrat = lsStrategy;
			this.deqStrat = deqStrat;
		}
		else throw new IllegalArgumentException();
		
		if(x < 0 && y < 0) throw new IllegalArgumentException();
		else 
		{
			this.x = x;
			this.y = y;
		}
		
		this.lastlight = 0;
		this.tlight = -1;
		this.roads = new ArrayList<Road>();
		this.vehiclelist = new ArrayList<List<Vehicle>>();
		this.map = new HashMap<Junction, Road>();
		this.maproads = new HashMap<Road, List<Vehicle>>();
	}

	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public List<List<Vehicle>> getVehicleList()
	{
		return this.vehiclelist;
	}
	
	public int getGreenLightIndex()
	{
		return tlight;
	}
	
	public List<Road> getInRoads()
	{
		return this.roads;
	}
	
	public void addIncomingRoad(Road r)
	{
		if(r.getDest() != this) throw new IllegalArgumentException();
		else
		{
			roads.add(r);
			LinkedList<Vehicle> cola = new LinkedList<>();
			vehiclelist.add(cola);
			maproads.put(r, cola);
		}
	}
	
	public void addOutgoingRoad(Road r)
	{
		for(Road it : roads)
		{
			if(it != r && it.getDest() == r.getDest())
			{
				throw new IllegalArgumentException();
			}
		}
		
		if(r.getSrc() == this)
		{
			map.put(r.getDest(),r);
		}
		else throw new IllegalArgumentException();
	}
	
	public void enter(Vehicle v)
	{
		vehiclelist.get(vehiclelist.indexOf(maproads.get(v.getRoad()))).add(v);	
		
	}
	
	public Road roadTo(Junction j)
	{
		Road r = map.get(j);
		return r;
		
	}
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		int i;
		
		if(tlight != -1 && !vehiclelist.get(tlight).isEmpty())
		{
			List<Vehicle> lista = deqStrat.dequeue(vehiclelist.get(tlight));
			for(int j = lista.size()-1; j >= 0; j--)
			{
				lista.get(j).moveToNextRoad();
				vehiclelist.get(tlight).remove(j);
				lista.remove(j);
			}
		}
	
		
		i = lightStrat.chooseNextGreen(roads, vehiclelist, tlight, lastlight, time);
		
		if(tlight != i)
		{
			tlight = i;
			lastlight = time;
		}
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		JSONObject j = new JSONObject();
		JSONArray ja = new JSONArray();
		JSONArray ja2 = new JSONArray();
		
		j.put("id", this._id);
		if(tlight != -1) j.put("green", this.tlight);
		else j.put("green", "none");
		
		int i = 1;
		for(List<Vehicle> l : vehiclelist)
		{
			JSONObject j2 = new JSONObject();
			j2.put("road", "r" + i);
			for(Vehicle v : l)
			{
				ja2.put(v);
			}
			j2.put("vehicles", ja2);
			ja.put(j2);
			i++;
		}
		j.put("queues", ja);
		return j;
	}

}
