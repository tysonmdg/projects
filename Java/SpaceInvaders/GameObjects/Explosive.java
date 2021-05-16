package GameObjects;

import game.Game;

public class Explosive extends AlienShip{
	private int damage;
	public Explosive(Game game, int x, int y, int points, int cycles, int c, int sentido, int live, boolean isInF, int shipsOn) {
		super(game, x, y, live , points, cycles);
		this.cyclesToMove = c;
		this.sentido = sentido;
		this.damage = 1;
		isInFinalRow = isInF;
		shipsOnBorder = shipsOn;
	}

	public boolean performAttack(GameObject other)
	{
		if(!this.isOnPosition(other.x, other.y))
		{
			other.receiveExplosiveAttack(this.damage);
		}
		return true;
	}
	
	public boolean receiveMissileAttack(int damage)
	{
		this.live-=damage;
		if(!this.isAlive()) 
			{ 
			this.getPoints();
			game.Explosive(this);
			}
		return true;
	}
	
	public String toString()
	{
		String pintar;
		pintar = "E[" + this.getLive() + "]";
		return pintar;
	}
	
	public String stringify() {
		String pintar;
		pintar = "E;" + this.x +","+ this.y +";" +this.live+";"
		+ this.cyclesToMove+";"+ this.sentido;
		return pintar;
	}
}
