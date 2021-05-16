package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private List<Junction> itinerary;
	private int maximum;
	private int current;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contaminationClass;
	private int totalCont;
	private int totalTravelled;
	private int cont;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary)
	{
		super(id);
		if(maxSpeed < 0) throw new IllegalArgumentException("maxSpeed has to be positive");
		else this.maximum = maxSpeed;
		if(contClass > 10 || contClass < 0) throw new IllegalArgumentException("contClass has to be a value between 0 and 10");
		else this.contaminationClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary)); 
		this.status = VehicleStatus.PENDING;
		this.road = null;
		this.location = 0;
		this.current = 0;
		this.totalCont = 0;
		this.totalTravelled = 0;
		this.cont = 0;
		
	}

	void setSpeed(int s)
	{	
		if(s<0)
		{
			throw new IllegalArgumentException("Cannot set negative speed");
		}
		else
		{
			if(s>this.maximum) this.current = this.maximum;
			else this.current = s;
		}
		
	}
	
	void setContaminationClass(int c)
	{
		if(c > 10 || c < 0) throw new IllegalArgumentException("contClass has to be a value between 0 and 10");
		else this.contaminationClass = c;
	}
	@Override
	void advance(int time) {
		if(this.status == VehicleStatus.TRAVELING)
		{
			int locVel = this.location + this.current;
			if(locVel < road.getLength()) 
				{
				this.totalCont = (this.contaminationClass * (locVel - this.location));
				road.addContamination(this.totalCont);
				this.totalTravelled += (locVel - this.location);
				this.location = locVel;
				}
			else 
				{
				this.totalCont = (this.contaminationClass * (road.getLength() - this.location));
				road.addContamination(this.totalCont);
				this.totalTravelled += (road.getLength() - this.location);
				this.location = road.getLength();
				}
			
			if(this.location == road.getLength())
			{
				if(this.cont + 1 == itinerary.size()) this.status = VehicleStatus.ARRIVED;
				else
				{
					this.status = VehicleStatus.WAITING;
					this.current = 0;
					itinerary.get(cont).enter(this);
				}
				
			}
			
		}
		
	}
	
	void moveToNextRoad()
	{
		if(this.status == VehicleStatus.WAITING)
		{
			this.current = 0;
			road.exit(this);
			this.road = itinerary.get(this.cont).roadTo(itinerary.get(this.cont+1));
			this.cont++;
			this.status = VehicleStatus.TRAVELING;
			this.location = 0;
			road.enter(this);
		
		}
		else if (this.status == VehicleStatus.PENDING)
		{
			this.road = itinerary.get(0).roadTo(this.itinerary.get(1));
			this.cont++;
			road.enter(this);
			this.status = VehicleStatus.TRAVELING;
		}
		else throw new IllegalArgumentException("Illegal action");
	}

	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("id", this._id);
		j.put("speed", this.current);
		j.put("distance", this.totalTravelled);
		j.put("co2", this.totalCont);
		j.put("class", this.contaminationClass);
		j.put("status", this.status);
		if(this.status == VehicleStatus.TRAVELING || this.status == VehicleStatus.WAITING)
		{
			j.put("road", this.road.getId());
			j.put("location", this.location);
		}
		return j;
	}
	
	public int getSpeed()
	{
		return this.current;
	}
	
	public int getLocation()
	{
		return this.location;
	}
	
	public int getContClass()
	{
		return this.contaminationClass;
	}
	
	public Road getRoad()
	{
		return this.road;
	}
	
	public List<Junction> getItinerary()
	{
		return this.itinerary;
	}
	
	public VehicleStatus getStatus()
	{
		return this.status;
	}

	public int getTotalTravelled() {
		// TODO Auto-generated method stub
		return this.totalTravelled;
	}

	public int getMaximumSpeed() {
		// TODO Auto-generated method stub
		return this.maximum;
	}

	public int getTotalCont() {
		// TODO Auto-generated method stub
		return this.totalCont;
	}
	
	public int getCont()
	{
		return this.cont;
	}
}

