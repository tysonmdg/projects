package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
	
	
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		// tables
		JPanel eventsView =
				createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		
		JPanel vehiclesView =
				createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);
		
		JPanel roadsView =
				createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Events");
		roadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);
		
		JPanel junctionsView =
				createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Events");
		junctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);
		// TODO add other tables
		// ...
		
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		
		JPanel mapbyroadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map");
		mapbyroadView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapbyroadView);
		// TODO add a map for MapByRoadComponent
		// ...
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		Border borde = BorderFactory.createTitledBorder(title);
		
		p.setBorder(borde);
		p.add(new JScrollPane(c));
		
		return p;
	}
	
	public MainWindow getInstance()
	{
		return this;
	}
}

