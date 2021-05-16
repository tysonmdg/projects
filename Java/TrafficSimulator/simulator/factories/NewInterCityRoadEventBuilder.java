package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event>{

	public NewInterCityRoadEventBuilder(String type) {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		Weather w = Weather.valueOf(data.getString("weather"));
		if(data.has("time" )&& data.has("id") && data.has("src")&& data.has("dest")&& data.has("length")
				&&data.has("co2limit")&&data.has("maxspeed") && data.has("weather"))
		{
			return new NewInterCityRoadEvent(data.getInt("time"), data.getString("id"),
					data.getString("src"),data.getString("dest"),data.getInt("length"),data.getInt("co2limit"),data.getInt("maxspeed")
					,w);
		}
		else return null;
	}

}
