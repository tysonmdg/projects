package GameObjects;

import game.Game;


public class Ovni extends EnemyShip{

	private boolean available;
	
	public Ovni(Game game, int x, int y) {
		super(game, x, y, 1, 25);
		this.available = false;
	}

	public void move()
	{
		if(this.available) this.y--;
		if(this.isOut()) 
			{
			this.available = false;
			this.y = 9;
			}
	}
	
	public void enable()
	{
		this.available = true;
	}
	
	public boolean getAvailable()
	{
		return this.available;
	}
	
	public void computerAction()
	{
		if(IExcecuteRandomActions.canGenerateRandomOvni(game) && !getAvailable())
		{
			enable();
		}
	}
	
	public String toString()
	{
		if(getAvailable())
		{
			String pintar;
			pintar = "O[" + this.live + "]";
			return pintar;
		}
		
		return " ";
	}
	
	public String stringify() {
		String pintar;
		pintar = "O;" + this.x +","+ this.y +";" +this.live;
		return pintar;
	}
	
	public boolean receiveMissileAttack(int damage)
	{
		this.available = false;
		this.y = 9;
		if(!this.isAlive()) this.getPoints();
		game.enableShockWave();
		return true;
	}
	
	public boolean receiveFlameAttack(int damage)
	{
		this.live-=damage;
		if(!this.isAlive()) this.getPoints();
		game.enableShockWave();
		return true;
	}
}
