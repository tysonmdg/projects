package simulator.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver{


	private static final long serialVersionUID = 1L;
	private Controller controller;
	private boolean _stopped;
	private JToolBar toolbar;
	private JFileChooser eventsfile;
	private JButton load;
	private JButton changeCO2;
	private JButton changeWeather;
	private JButton run;
	private JButton stop;
	private JButton exit;
	private JSpinner ticks;
	private RoadMap roadmap;
	private int steps;
	
	public ControlPanel(Controller c)
	{
		this.controller = c;
		this.controller.addObserver(this);
		initGUI();
	}
	
	private void initGUI()
	{
		this.toolbar = new JToolBar();
		this.eventsfile = new JFileChooser();
		
		load = new JButton();
		load.setToolTipText("Load Events");
		load.setIcon(new ImageIcon("resources/icons/open.png"));
		load.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						showOpen();
						
					}
				});
		toolbar.add(load);
		
		changeCO2 = new JButton();
		changeCO2.setToolTipText("Change CO2 Class");
		changeCO2.setIcon(new ImageIcon("resources/icons/co2class.png"));
		changeCO2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCO2Class();
			}
			
		});
		toolbar.add(changeCO2);
		
		changeWeather = new JButton();
		changeWeather.setToolTipText("Change Road Weather");
		changeWeather.setIcon(new ImageIcon("resources/icons/weather.png"));
		changeWeather.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeWeather();
			}
			
		});
		toolbar.add(changeWeather);
		
		run = new JButton();
		run.setToolTipText("Run");
		run.setIcon(new ImageIcon("resources/icons/run.png"));
		run.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						_stopped = false;
						run_sim((int) ticks.getValue());
					}
			
				});
		toolbar.add(run);
		
		stop = new JButton();
		stop.setToolTipText("Stop");
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		stop.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						stop();
					}
			
				});
		toolbar.add(stop);
		
		ticks = new JSpinner(new SpinnerNumberModel(10,1,500,1));
		ticks.setMaximumSize(new Dimension(80,40));
		ticks.setMinimumSize(new Dimension(80,40));
		ticks.setPreferredSize(new Dimension(80,40));
		
		toolbar.add(ticks);
		
		toolbar.add(Box.createGlue());
		toolbar.addSeparator();
		
		exit = new JButton();
		exit.setToolTipText("Exit");
		exit.setIcon(new ImageIcon("resources/icons/exit.png"));
		exit.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						exit();
					}
			
				});
		toolbar.add(exit);
		this.add(toolbar);
		
		this.setVisible(true);
	}

	
	private void showOpen()
	{
		if(eventsfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			this.controller.reset();
			try {
				this.controller.loadEvents(new FileInputStream(eventsfile.getSelectedFile()));
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		}
	}
	
	private void changeCO2Class()
	{
		ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog();
		
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		List<Integer> contClass = new ArrayList<Integer>();
		List<Pair<String, Integer>> cs = new ArrayList<>();
		
		for(Vehicle v : roadmap.getVehicles())
		{
			vehicles.add(v);
		}
		
		for(int i = 0; i < 11; i++)
		{
			contClass.add(i);
		}
		
		int status = dialog.open(vehicles, contClass);
	
		if(status == 1)
		{
			String id = dialog.getVehicleId();
			int contC = dialog.getContClass();
			int time = steps + dialog.getSteps();
			cs.add(new Pair<String, Integer>(id, contC));
			try
			{
				controller.addEvent(new NewSetContClassEvent(time, cs));
			} catch (IllegalArgumentException e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		}
		else 
		{
			
			
		}
	}
	
	private void changeWeather()
	{
		ChangeWeatherDialog dialog = new ChangeWeatherDialog();
		
		List<Road> roads = new ArrayList<Road>();
		List<Weather> weather = new ArrayList<Weather>();
		List<Pair<String, Weather>> ws = new ArrayList<>();
		
		for(Road v : roadmap.getRoads())
		{
			roads.add(v);
		}
		
		for(Weather w : Weather.values())
		{
			weather.add(w);
		}
		
		int status = dialog.open(roads, weather);
		
		if(status == 1)
		{
			String id = dialog.getRoadId();
			Weather we = dialog.getWeather();
			int time = steps + dialog.getSteps();
			ws.add(new Pair<String, Weather>(id, we));
			System.out.println(time + id);
			try
			{
				controller.addEvent(new SetWeatherEvent(time, ws));
			} catch (IllegalArgumentException e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		}
	}
	
	private void run_sim(int n)
	{
		if (n > 0 && !_stopped) {
			try {
				enableToolBar(false);
				controller.run(1);
			} catch (Exception e) {
			// TODO show error message
				JOptionPane.showMessageDialog(this, e.getMessage());
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
			@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} else {
				enableToolBar(true);
				_stopped = true;
			}

	}
	private void enableToolBar(boolean b) {
		load.setEnabled(b);
		changeCO2.setEnabled(b);
		changeWeather.setEnabled(b);
	}

	private void stop()
	{
		_stopped = true;
	}
	
	private void exit()
	{
		if(JOptionPane.showOptionDialog(this, "Do you want to exit?", "Exit", 0, 3 , null, null, null) == 0)
			System.exit(0);
	}
	
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.roadmap = map;
		this.steps = time;
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.roadmap = map;
		this.steps = time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.roadmap = map;
		this.steps = time;
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
