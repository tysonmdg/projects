package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private static final long serialVersionUID = 1L;

	private Controller controller;
	private List<Road> r;
	private String[] column = { "id","length","weather","maximumSpeed","currentSpeed", "totalCont", "contLimit" };
	
	public RoadsTableModel(Controller c)
	{
		this.controller = c;
		controller.addObserver(this);
		this.r = new ArrayList<>();
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.r.size();
	}
	
	public String getColumnName(int c)
	{
		return column[c];
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return column.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

		if(columnIndex == 0)
		{
			return r.get(rowIndex).getId();
		}
		else if(columnIndex == 1)
		{
			return r.get(rowIndex).getLength();
		}
		else if(columnIndex == 2)
		{
			return r.get(rowIndex).getWeather();
		}
		else if(columnIndex == 3)
		{
			return r.get(rowIndex).getMaximumSpeed();
		}
		else if(columnIndex == 4)
		{
			return r.get(rowIndex).getCurrentSpeed();
		}
		else if(columnIndex == 5)
		{
			return r.get(rowIndex).getTotalCO2();
		}
		else
		{
			return r.get(rowIndex).getCO2Limit();
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.r = map.getRoads();
		fireTableDataChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.r = map.getRoads();
		fireTableDataChanged();	
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
