package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	public NewVehicleEventBuilder(String type) {
		// TODO Auto-generated constructor stub
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		if(data.has("time" )&& data.has("id") && data.has("itinerary") && data.has("maxspeed") && data.has("class"))
		{
			List<String> list = new ArrayList<>();
			JSONArray ja = data.getJSONArray("itinerary");
			for(int i = 0; i < ja.length(); i++)
			{
				list.add(ja.getString(i));
			}
			return new NewVehicleEvent(data.getInt("time"),data.getString("id"),
					data.getInt("maxspeed"),data.getInt("class"),list);
		}
		else return null;
	}

}
