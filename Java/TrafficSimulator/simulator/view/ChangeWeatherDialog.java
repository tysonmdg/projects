package simulator.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import simulator.model.Road;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Road> _roads;
	private DefaultComboBoxModel<Road> _roadsModel;
	private JComboBox<Weather> _weather;
	private DefaultComboBoxModel<Weather> _weatherModel;
	private JSpinner _ticks;
	private int _status;
	
	public ChangeWeatherDialog()
	{	
		initGUI();
	}
	
	public void initGUI()
	{
		
		setTitle("Change Road Weather");
		JPanel mainP = new JPanel();
		mainP.setLayout(new BoxLayout(mainP, BoxLayout.Y_AXIS));
		setContentPane(mainP);
		
		JLabel helpMsg = new JLabel("Schedule an event to change the weather of a road "
				+ "after a given number of simulation ticks from now");
		
		mainP.add(helpMsg);
		
		mainP.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainP.add(viewsPanel);
		
		mainP.add(Box.createRigidArea(new Dimension(0, 20)));
		
		_roadsModel = new DefaultComboBoxModel<>();
		_roads = new JComboBox<>(_roadsModel);
		_weatherModel = new DefaultComboBoxModel<>();
		_weather = new JComboBox<>(_weatherModel);
		_ticks = new JSpinner();
		
		viewsPanel.add(_roads);
		viewsPanel.add(_weather);
		viewsPanel.add(_ticks);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainP.add(buttonsPanel);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_roadsModel.getSelectedItem() != null && _weatherModel.getSelectedItem() != null) {
					_status = 1;
					ChangeWeatherDialog.this.setVisible(false);
				}
			}
		});
		buttonsPanel.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		
		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	public int open(List<Road> v, List<Weather> c)
	{
		_roadsModel.removeAllElements();
		_weatherModel.removeAllElements();
		for(Road r : v)
		{
			_roadsModel.addElement(r);
		}
		
		for(Weather w : c)
		{
			_weatherModel.addElement(w);
		}
		
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
		setVisible(true);
		
		return _status;
		
	}
	
	protected String getRoadId()
	{
		Road v = (Road) _roadsModel.getSelectedItem();
		String id = v.getId();
		return id;
	}
	
	protected Weather getWeather()
	{
		return (Weather) _weatherModel.getSelectedItem();
	}
	
	protected int getSteps()
	{
		return (int) _ticks.getValue();
	}
}

