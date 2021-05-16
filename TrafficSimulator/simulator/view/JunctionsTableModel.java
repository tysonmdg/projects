package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private static final long serialVersionUID = 1L;

	private Controller controller;
	private List<Junction> j;
	private String[] column = { "id","roadGreen","vehiclesRoadList"};
	
	public JunctionsTableModel(Controller c)
	{
		this.controller = c;
		controller.addObserver(this);
		this.j = new ArrayList<>();
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.j.size();
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
			return j.get(rowIndex).getId();
		}
		else if(columnIndex == 1)
		{
			int i = j.get(rowIndex).getGreenLightIndex();
			if(i == -1)
				return "NONE";
			else return j.get(rowIndex).getInRoads().get(i);
		}
		else
		{
			String s = "";
			int i = 0;
			for( List<Vehicle> v : j.get(rowIndex).getVehicleList())
			{
				s += "r" + i + ":[";
				for(int x = 0 ; x < v.size(); x++)
				{
					s += v.get(x).getId();
					if(x + 1 != v.size()) s += ",";
				}
				s += "]";
				i++;
			}
			return s;
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.j = map.getJunctions();
		fireTableDataChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.j = map.getJunctions();
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
