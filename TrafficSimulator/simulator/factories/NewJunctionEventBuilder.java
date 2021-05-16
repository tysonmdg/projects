package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	Factory<LightSwitchingStrategy> lssFactory;
	Factory<DequeuingStrategy> dqsFactory;
	public NewJunctionEventBuilder(String type, Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super(type);
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		if(data.has("time" )&& data.has("id") && data.has("ls_strategy")&& data.has("dq_strategy")&& data.has("coor"))
		{
			JSONArray ja = data.getJSONArray("coor");
			int x, y;
			x = ja.getInt(0);
			y = ja.getInt(1);
			return new NewJunctionEvent(data.getInt("time"), data.getString("id"),
					lssFactory.createInstance(data.getJSONObject("ls_strategy")),dqsFactory.createInstance(data.getJSONObject("dq_strategy")),
					x, y);
		}
		else return null;
	}

}
