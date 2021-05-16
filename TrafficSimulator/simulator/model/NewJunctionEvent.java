package simulator.model;

public class NewJunctionEvent extends Event{
	private String id;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int x;
	private int y;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy,
			int x, int y)
	{
		super(time);
		this.id = id;
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.x = x;
		this.y = y;
	}

	public String toString()
	{
		return "New Junction " + id;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		map.addJunction(new Junction(id, lsStrategy,dqStrategy,x,y));
	}}
