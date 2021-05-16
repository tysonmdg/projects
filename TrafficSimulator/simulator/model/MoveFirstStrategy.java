package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Auto-generated method stub
		ArrayList<Vehicle> v = new ArrayList<Vehicle>();
		v.add(q.get(0));
		return v;
	}
}
