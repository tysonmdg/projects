package GameObjects;

import game.Game;

public class flameThrower extends Weapon {
	
	public flameThrower(Game game, GameObject object)
	{
		super(game, object.getX(), object.getY(), 2);
	}
	
	public boolean performAttack(GameObject other)
	{
		other.receiveFlameAttack(this.getDamage());
		this.live--;
		return true;
	}
}
