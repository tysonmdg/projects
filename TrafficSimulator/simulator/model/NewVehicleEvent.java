package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
	
	private String id;
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) 
	{ 
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}
	
	public String toString()
	{
		return "New Vehicle " + id;
	}
	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		List<Junction> list = new ArrayList<>();
		for(String i : itinerary)
		{
			list.add(map.juncmap.get(i));
		}
		Vehicle v = new Vehicle(id,maxSpeed, contClass, list);
		map.addVehicle(v);
		v.moveToNextRoad();
		
		
	}
}
