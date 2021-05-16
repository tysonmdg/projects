package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder(String type) {
		// TODO Auto-generated constructor stub
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		if(data.has("time" )&& data.has("info"))
		{
			JSONArray ja = data.getJSONArray("info");
			List<Pair<String,Weather>> list = new ArrayList<>();
			for(int i = 0; i < ja.length(); i++)
			{
				JSONObject j = ja.getJSONObject(i);
				Weather w = Weather.valueOf(j.getString("weather"));
				Pair<String,Weather> p = new Pair<String, Weather>(j.getString("road"), w);
				list.add(p);
			}
			return new SetWeatherEvent(data.getInt("time"),list);
		}
		else return null;
	}

}
