package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	private int ticks;
	public MostCrowdedStrategy(int timeSlot)
	{
		this.ticks = timeSlot;
	}
	
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		int longest = 0;
		int size = 0;
		
		if(roads.isEmpty()) return - 1;
		else if(currGreen == - 1)
		{
			for(int i = 0; i< qs.size();i++)
			{
				if(size < qs.get(i).size()) 
					{
						size = qs.get(i).size();
						longest = i;
					}
			}
			return longest;
		}
		else if((currTime - lastSwitchingTime) < this.ticks) return currGreen;
		else
		{
			for(int i = (currGreen + 1) % qs.size(); i == currGreen % qs.size() ; i++)
			{
				if(size < qs.get(i).size()) 
				{
					size = qs.get(i).size();
					longest = i;
				}
				if(i+1 == qs.size()) i = 0;
			}
			return longest;
		}
	}
}
