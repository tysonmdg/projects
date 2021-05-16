package GameObjects;

import game.Game;

public class Weapon extends GameObject implements IAttack{

	private int damage;
	public Weapon(Game game, int x, int y, int damage) {
		super(game, x, y, 1);
		this.damage = damage;
	}

	public int getDamage()
	{
		return this.damage;
	}
	
	public void computerAction() {
	}

	public void onDelete() {
	}

	public void move() {
	}

	public String toString() {
		return null;
	}
	
	public String stringify()
	{
		return null;
	}
}
