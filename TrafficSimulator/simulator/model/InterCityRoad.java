package simulator.model;

public class InterCityRoad extends Road {


	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		int x;
		
		if(this.weatherCond == Weather.SUNNY) x = 2;
		else if(this.weatherCond == Weather.CLOUDY) x = 3;
		else if(this.weatherCond == Weather.RAINY) x = 10;
		else if(this.weatherCond == Weather.WINDY) x = 15;
		else  x = 20;
		
		this.totalCont = (int)(((100 - x)/100.0) * this.totalCont);
	}

	@Override
	void updateSpeedLimit() {
		if(this.totalCont > this.contAlarm) this.currentLimit = (int)(this.maximumLimit*0.5);
		else this.currentLimit = this.maximumLimit;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int speed;
		if(this.weatherCond == Weather.STORM) speed = (int)(this.currentLimit * 0.8);
		else speed = this.currentLimit;
		return speed;
	}
}
