package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Controller controller;
	private List<Vehicle> v;
	private String[] column = { "id","status","itinerary","co2Class","maximumSpeed","currentSpeed", "totalCont", "totalTravelled" };
	
	public VehiclesTableModel(Controller c)
	{
		this.controller = c;
		controller.addObserver(this);
		this.v = new ArrayList<>();
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.v.size();
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
			return v.get(rowIndex).getId();
		}
		else if(columnIndex == 1)
		{
			if(v.get(rowIndex).getStatus() == VehicleStatus.ARRIVED)
				return "Arrived";
			else if(v.get(rowIndex).getStatus() == VehicleStatus.PENDING)
				return "Pending";
			else if(v.get(rowIndex).getStatus() == VehicleStatus.TRAVELING)
				return v.get(rowIndex).getRoad() + ":" + v.get(rowIndex).getLocation();
			else 
				return "Waiting" + v.get(rowIndex).getItinerary().get(v.get(rowIndex).getCont());
		}
		else if(columnIndex == 2)
		{
			return v.get(rowIndex).getItinerary();
		}
		else if(columnIndex == 3)
		{
			return v.get(rowIndex).getContClass();
		}
		else if(columnIndex == 4)
		{
			return v.get(rowIndex).getMaximumSpeed();
		}
		else if(columnIndex == 5)
		{
			return v.get(rowIndex).getSpeed();
		}
		else if(columnIndex == 6)
		{
			return v.get(rowIndex).getTotalCont();
		}
		else
		{
			return v.get(rowIndex).getTotalTravelled();
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.v = map.getVehicles();
		fireTableDataChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.v = map.getVehicles();
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
