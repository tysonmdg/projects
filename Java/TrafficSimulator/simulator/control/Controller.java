package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator trafficsim;
	private Factory<Event> eventsfactory;
	
	public Controller(TrafficSimulator t, Factory<Event> e)
	{
		if(t != null && e != null)
		{
			this.trafficsim = t;
			this.eventsfactory = e;
		}
		else throw new IllegalArgumentException();
	}
	
	public void reset()
	{
		trafficsim.reset();
	}
	
	public void addObserver(TrafficSimObserver o)
	{
		trafficsim.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o)
	{
		trafficsim.removeObserver(o);
	}
	
	public void addEvent(Event e)
	{
		trafficsim.addEvent(e);
	}
	
	public void loadEvents(InputStream i)
	{
		JSONObject jo = new JSONObject(new JSONTokener(i));
		if(!jo.has("events")) throw new IllegalArgumentException();
		JSONArray ja = jo.getJSONArray("events");
		
		for(int j = 0; j < ja.length(); j++)
		{
			trafficsim.addEvent(eventsfactory.createInstance(ja.getJSONObject(j)));
		}
	}
	
	public void run(int n, OutputStream o)
	{
		JSONObject j = new JSONObject();
		JSONArray ja = new JSONArray();
		for(int i = 0; i < n;i++)
		{
			trafficsim.advance();
			ja.put(trafficsim.report());
		}
		j.put("states", ja);
		PrintStream p = new PrintStream(o);
		p.print(j.toString(3));
	}

	public void run(int n) {
		for(int i = 0; i < n;i++)
		{
			trafficsim.advance();
			
		}
	}
}
