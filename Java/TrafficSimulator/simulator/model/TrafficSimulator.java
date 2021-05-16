package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	private RoadMap roadmap;
	private List<Event> eventlist;
	private int step;
	private List<TrafficSimObserver> observadores;
	
	public TrafficSimulator()
	{
		this.roadmap = new RoadMap();
		this.eventlist = new SortedArrayList<>();
		this.step = 0;
		this.observadores = new ArrayList<>();
	}
	
	public void addEvent(Event e)
	{
		eventlist.add(e);
		notifyOnEventAdded(e);
	}
	
	public void advance()
	{
		this.step++;
		notifyOnAdvanceStart();
		List<Event> e = new SortedArrayList<>();
		try
		{
			for(Event event : eventlist)
			{
				if(event.getTime() == this.step)
				{
					e.add(event);
					event.execute(roadmap);
				}
			}
			
			for(Event event : e)
			{
				eventlist.remove(event);
			}

			for(Junction j : roadmap.junctions)
			{
				j.advance(step);
			}
			
			for(Road r: roadmap.roads)
			{
				r.advance(step);
			}
		}catch(IllegalArgumentException err)
		{
			notifyOnError(err.getMessage());
			throw new IllegalArgumentException (err.getMessage());
			
		}
		
		notifyOnAdvanceEnd();
	}
	
	public void reset()
	{
		new TrafficSimulator();
		notifyOnReset();
	}
	
	public JSONObject report()
	{
		JSONObject j = new JSONObject();
		
		j.put("time", this.step);
		j.put("state", roadmap.report());
		
		return j;
	}

	public void notifyOnAdvanceStart()
	{
		for(TrafficSimObserver o : observadores)
		{
			o.onAdvanceStart(roadmap, eventlist, step);
		}
	}
	
	public void notifyOnAdvanceEnd()
	{
		for(TrafficSimObserver o : observadores)
		{
			o.onAdvanceEnd(roadmap, eventlist, step);
		}
		
	}
	
	public void notifyOnEventAdded(Event e)
	{
		for(TrafficSimObserver o : observadores)
		{
			o.onEventAdded(roadmap, eventlist, e, step);
		}
	}
	
	public void notifyOnReset()
	{
		for(TrafficSimObserver o : observadores)
		{
			o.onReset(roadmap, eventlist, step);
		}
	}
	
	public void notifyOnError(String err)
	{
		for(TrafficSimObserver o : observadores)
		{
			o.onError(err);
		}
	}
	
	
	public void addObserver(TrafficSimObserver o) {
		observadores.add(o);
		o.onRegister(roadmap, eventlist, step);
	}


	public void removeObserver(TrafficSimObserver o) {
		observadores.remove(o);
	}
	
}
