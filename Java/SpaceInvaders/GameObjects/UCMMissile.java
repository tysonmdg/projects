package GameObjects;

import game.Game;

public class UCMMissile extends Weapon {

	public UCMMissile(Game game, int x, int y, int damage) {
			super(game, x, y, damage);
	}

	public void move()
	{
		this.x--;
		if(this.isOut()) 
			{
				game.enableMissile();
				this.live--;
			}
	}
	
	public boolean performAttack(GameObject other)
	{
		other.receiveMissileAttack(this.getDamage());
		this.live--;
		game.enableMissile();
		return true;
		
	}
	
	public boolean receiveBombAttack(int damage)
	{
		this.live-=damage;
		game.enableMissile();
		return true;
	}
	
	public String toString()
	{
		String pintar;
		pintar = "oo";
		return pintar;
	}
	
	public String stringify() {
		String pintar;
		pintar = "M;" + this.x +","+ this.y;
		return pintar;
	}
}


