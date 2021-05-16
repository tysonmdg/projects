package simulator.model;

public class NewCityRoadEvent extends Event{

	private String id;
	private String srcJun;
	private String destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;
	
	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, 
			int maxSpeed, Weather weather) 
	{ 
		super(time);
		this.id = id;
		this.srcJun = srcJun;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
		
	}
	
	public String toString()
	{
		return "New City Road " + id;
	}
	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		Road r = new CityRoad(id,map.getJunction(srcJun), map.getJunction(destJunc), length, co2Limit, maxSpeed, weather);
		map.addRoad(r);
		
	}


}
