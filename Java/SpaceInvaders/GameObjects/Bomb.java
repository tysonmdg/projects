package GameObjects;

import game.Game;

public class Bomb extends Weapon {
	private DestroyerShip destroyer;
	
	public Bomb(Game game, int x, int y, DestroyerShip destroyer) {
		super(game, x, y, 1);
		this.destroyer = destroyer;
	}

	public void move()
	{
		this.x++;
		if(this.isOut()) 
			{
			this.live--;
			destroyer.activateBomb();
			}
	}
	
	public boolean performAttack(GameObject other)
	{
		other.receiveBombAttack(this.getDamage());
		destroyer.activateBomb();
		this.live--;
		return true;
	}
	
	public boolean receiveMissileAttack(int damage)
	{
		this.live-=damage;
		destroyer.activateBomb();
		return true;
	}
	
	public String toString()
	{
		String pintar;
		pintar = "*";
		return pintar;
	}
	
	public String stringify() {
		String pintar;
		pintar = "B;" + this.x +","+ this.y;
		return pintar;
	}
}
