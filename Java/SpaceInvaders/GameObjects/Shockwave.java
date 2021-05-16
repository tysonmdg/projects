package GameObjects;

import game.Game;

public class Shockwave extends Weapon {

	public Shockwave(Game game) {
		super(game, 10, 10, 1);
	}
	
	public boolean performAttack(GameObject other)
	{
		other.receiveShockWaveAttack(this.getDamage());
		this.live--;
		return true;
	}

}
