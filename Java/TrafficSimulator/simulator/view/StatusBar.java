package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller c;
	private JLabel t;
	private JLabel event;

	public StatusBar(Controller c)
	{
		this.c = c;
		initGUI();
		this.c.addObserver(this);
		
	}

	private void initGUI()
	{
		this.setLayout(new FlowLayout());
		
		t = new JLabel("Time: 0");
		event = new JLabel("");
	
		this.add(t);
		this.add(event);
		
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		t.getText();
		event.setText("");
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		t.setText("Time: " + time);
		event.setText("");
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		t.getText();
		event.setText(e.toString());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
		t.setText("Time: " + time);
		event.setText("");
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		t.getText();
		event.setText("");
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		t.getText();
		event.setText(err);
	}

}
