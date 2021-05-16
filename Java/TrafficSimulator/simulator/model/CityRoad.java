package simulator.model;


public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		if(this.weatherCond == Weather.WINDY || this.weatherCond == Weather.STORM)
		{
			this.reduce(10);
		}
		else this.reduce(2);
	}

	@Override
	void updateSpeedLimit() {
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		// TODO Auto-generated method stub		
		int speed;
		speed = (int)(((11 - v.getContClass()) / 11.0) * this.maximumLimit);
		return speed;
	}
	

}
