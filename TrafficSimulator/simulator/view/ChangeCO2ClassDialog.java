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

import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<Vehicle> _vehicles;
	private DefaultComboBoxModel<Vehicle> _vehiclesModel;
	private JComboBox<Integer> _co2Class;
	private DefaultComboBoxModel<Integer> _co2ClassModel;
	private JSpinner _ticks;
	private int _status;
	
	public ChangeCO2ClassDialog()
	{
		initGUI();
	}
	
	public void initGUI()
	{
		
		setTitle("Change CO2 Class");
		JPanel mainP = new JPanel();
		mainP.setLayout(new BoxLayout(mainP, BoxLayout.Y_AXIS));
		setContentPane(mainP);
		
		JLabel helpMsg = new JLabel("Schedule an event to change the CO2 class of a vehicle "
				+ "after a given number of simulation ticks from now");
		
		mainP.add(helpMsg);
		
		mainP.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainP.add(viewsPanel);
		
		mainP.add(Box.createRigidArea(new Dimension(0, 20)));
		_vehiclesModel = new DefaultComboBoxModel<>();
		_vehicles = new JComboBox<>(_vehiclesModel);
		_co2ClassModel = new DefaultComboBoxModel<>();
		_co2Class = new JComboBox<>(_co2ClassModel);
		_ticks = new JSpinner();
		
		viewsPanel.add(_vehicles);
		viewsPanel.add(_co2Class);
		viewsPanel.add(_ticks);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainP.add(buttonsPanel);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_vehiclesModel.getSelectedItem() != null && _co2ClassModel.getSelectedItem() != null) {
					_status = 1;
					ChangeCO2ClassDialog.this.setVisible(false);
				}
			}
		});
		buttonsPanel.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		
		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	public int open(List<Vehicle> v, List<Integer> c)
	{
		_vehiclesModel.removeAllElements();
		_co2ClassModel.removeAllElements();
		for(Vehicle vehicle : v)
		{
			_vehiclesModel.addElement(vehicle);
		}
		
		for(int i : c)
		{
			_co2ClassModel.addElement(i);
		}
		
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
		
		setVisible(true);
		
		return _status;
		
	}
	
	protected String getVehicleId()
	{
		Vehicle v = (Vehicle) _vehiclesModel.getSelectedItem();
		String id = v.getId();
		return id;
	}
	
	protected int getContClass()
	{
		return (int) _co2ClassModel.getSelectedItem();
	}
	
	protected int getSteps()
	{
		return (int) _ticks.getValue();
	}
}
