package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{

	private List<Pair<String,Integer>> cs;
	
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) 
	{ 
		super(time);
		if(!cs.isEmpty()) this.cs = cs;
		else throw new IllegalArgumentException();
	}

	public String toString()
	{
		return "New Contamination Class Setted ";
	}
	
	@Override
	void execute(RoadMap map) {
		Vehicle v;
		for(Pair<String,Integer> c : cs)
		{
			if(map.vehiclemap.containsKey(c.getFirst()))
			{
				v = map.vehiclemap.get(c.getFirst());
				v.setContaminationClass(c.getSecond());
						
			}
			else throw new IllegalArgumentException();
		}
		
	}
}
