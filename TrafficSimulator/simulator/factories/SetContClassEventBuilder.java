package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder(String type) {
		// TODO Auto-generated constructor s
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		if(data.has("time" )&& data.has("info"))
		{
			JSONArray ja = data.getJSONArray("info");
			List<Pair<String,Integer>> list = new ArrayList<>();
			for(int i = 0; i < ja.length(); i++)
			{
				JSONObject j = ja.getJSONObject(i);
				Pair<String, Integer> p = new Pair<String, Integer>(j.getString("vehicle"), j.getInt("class"));
				list.add(p);
			}
			return new NewSetContClassEvent(data.getInt("time"), list);
		}
		else return null;
	}

}
