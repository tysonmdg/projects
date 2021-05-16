package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Event> events;
	private String[] column = { "Time", "Description" };
	private Controller controller;
	
	public EventsTableModel(Controller c)
	{
		this.controller = c;
		this.controller.addObserver(this);
		this.events = new ArrayList<Event>();
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.events.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return column.length;
	}

	public String getColumnName(int c)
	{
		return column[c];
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		String s;
		if(columnIndex == 0)
		{
			s = "" + events.get(rowIndex).getTime();
		}
		else 
		{
			s = events.get(rowIndex).toString();
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.events = events;
		fireTableDataChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		this.events = events;
		fireTableDataChanged();	
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.events = events;
		fireTableDataChanged();	
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.events = events;
		fireTableDataChanged();	
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
