package GameObjects;

import game.Game;

public class UCMSuperM extends UCMMissile {

	public UCMSuperM(Game game, int x, int y, int damage) {
		super(game, x, y, damage);
	}
	
	public String toString()
	{
		String pintar;
		pintar = "OO";
		return pintar;
	}
	
	public String stringify() {
		String pintar;
		pintar = "X;" + this.x +","+ this.y;
		return pintar;
	}
}
