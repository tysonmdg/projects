package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	private Junction srcJunc;
	private Junction destJunc;
	private int length;
	protected int maximumLimit;
	protected int currentLimit;
	protected int contAlarm;
	protected Weather weatherCond;
	protected int totalCont;
	List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int length,
			int contLimit, int maxSpeed, Weather weather)
	{
		super(id);
		if(srcJunc == null || destJunc == null || weather == null) throw new IllegalArgumentException("This argument cant be null");
		else
		{
			this.srcJunc = srcJunc;
			this.destJunc = destJunc;
			this.weatherCond = weather;
		}
		this.length = length;
		if(maxSpeed > 0) this.maximumLimit = maxSpeed;
		else throw new IllegalArgumentException("Max speed must be positive");
		this.currentLimit = 0;
		if(contLimit > 0) this.contAlarm = contLimit;
		else throw new IllegalArgumentException("Contamination limit must be positive");
		this.totalCont = 0;
		this.vehicles = new ArrayList<Vehicle>();
	}

	void enter(Vehicle v)
	{
		if(v.getSpeed() == 0 && v.getLocation() == 0)
		{
			vehicles.add(v);
		}
		else throw new IllegalArgumentException("Cant set this vehicle in the road");
		
	}
	
	void exit(Vehicle v)
	{
		vehicles.remove(v);
	}
	
	void setWeather(Weather w)
	{
		if(w != null)
		{
			this.weatherCond = w;
		}
		else throw new IllegalArgumentException("Weather cant be null");
	}
	
	void addContamination(int c)
	{
		if(c >= 0) this.totalCont += c;
		else throw new IllegalArgumentException("Contamination cant be negative");
	}
	
	void reduce(int c)
	{
		if(this.totalCont - c < 0) this.totalCont = 0;
		else this.totalCont -= c;
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for(Vehicle v : vehicles)
		{
			v.setSpeed(this.calculateVehicleSpeed(v));
			v.advance(time);
		}
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		JSONObject j = new JSONObject();
		JSONArray ja = new JSONArray();
		j.put("id", this._id);
		j.put("speedlimit", this.maximumLimit);
		j.put("weather", this.weatherCond);
		j.put("co2", this.totalCont);
		for(Vehicle v : vehicles)
		{
			ja.put(v.getId());
		}
		j.put("vehicle", ja);
		return j;
	}
	
	public int getLength()
	{
		return this.length;
	}
	
	public String getId()
	{
		return this._id;
	}

	public Junction getDest()
	{
		return this.destJunc;
	}
	
	public Junction getSrc()
	{
		return this.srcJunc;
	}
	
	public int getTotalCO2()
	{
		return this.totalCont;
	}
	
	public int getCO2Limit()
	{
		return this.contAlarm;
	}
	
	public Weather getWeather()
	{
		return this.weatherCond;
	}

	public int getMaximumSpeed() {
		// TODO Auto-generated method stub
		return this.maximumLimit;
	}

	public int getCurrentSpeed() {
		// TODO Auto-generated method stub
		return this.currentLimit;
	}
}
