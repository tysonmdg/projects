package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{
	private List<Pair<String,Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) 
	{ 
		super(time);
		if(!ws.isEmpty()) this.ws = ws;
		else throw new IllegalArgumentException();
	}

	public String toString()
	{
		return "New Weather Setted ";
	}
	@Override
	void execute(RoadMap map) {
		Road r;
		for(Pair<String,Weather> w : ws)
		{
			if(map.roadmap.containsKey(w.getFirst()))
			{
				r = map.roadmap.get(w.getFirst());
				r.setWeather(w.getSecond());
			}
			else throw new IllegalArgumentException();
		}
		
	}
}
