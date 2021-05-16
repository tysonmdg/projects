package GameObjects;

import game.Game;

public class EnemyShip extends Ship {
	private int puntos;
	
	public EnemyShip(Game game, int x, int y, int live, int puntos) {
		super(game, x, y, live);
		this.puntos = puntos;
	}
	
	public void getPoints()
	{
		game.receivePoints(this.puntos);
	}

	public void move()
	{
	}
	

}
