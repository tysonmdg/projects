package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private static final Color _BLACK_COLOR = Color.BLACK;
	
	private int x1;
	private int x2; 
	private int y;
	private int i;

	private RoadMap _map;

	private Image _car;
	private List<Image> images;

	MapByRoadComponent(Controller ctrl) {
		images = new ArrayList<Image>();
		setPreferredSize(new Dimension(300,200));
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		// TODO Auto-generated method stub
		_car = loadImage("car_front.png");
		for(int i = 0; i < 6; i++)
		{
			images.add(loadImage("cont_"+ i +".png"));
		}
		
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		int A, B;
		
		this.i = 50;
		this.x1 = 50;
		this.x2= getWidth()-100;
		this.y = (i+1);
		for(Road r : _map.getRoads())
		{
			g.setColor(_BLACK_COLOR);
			g.drawString(r.getId(), x1 - 20, y);
			g.drawLine(x1, y, x2, y);
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_RED_LIGHT_COLOR);
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				g.setColor(_GREEN_LIGHT_COLOR);
			}
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getSrc().getId(), x1, y - 6);
			g.drawString(r.getDest().getId(), x2, y - 6);
			g.drawImage(r.getWeather().getImage(),x2 + 6,y - 12, 32, 32, this);
			A = r.getTotalCO2();
			B = r.getCO2Limit();
			int C =  (int) Math.floor(Math.min((double) A/(1.0 + (double) B),1.0) / 0.19);
			g.drawImage(images.get(C), x2 + 40, y - 12, 32, 32, this);
			y+=50;
		}
		drawVehicles(g);
	}
	
	private void drawVehicles(Graphics g) {
		for (Vehicle v : _map.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {
				Road r = v.getRoad();
				y = 50*(_map.getRoads().indexOf(r)+1);
				int A = v.getLocation(), B = r.getLength();
				int X =  this.x1 + (int) ((this.x2 - this.x1) * ((double) A / (double) B));
	
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car (with circle as background) and it identifier
				g.drawImage(_car, X, y - 6, 16, 16, this);
				g.drawString(v.getId(), X, y - 6);
			}
		}
	}

	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void update(RoadMap map) {
		_map = map;
		repaint();
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		update(map);
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
