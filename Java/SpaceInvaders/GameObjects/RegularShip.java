package GameObjects;

import game.Game;

public class RegularShip extends AlienShip{

	private boolean explosive;
	
	public RegularShip(Game game, int x, int y, int cycles) {
		super(game, x, y, 2, 5, cycles);
		this.explosive = true;
	}

	public void computerAction()
	{
		GameObject object;
		if(IExcecuteRandomActions.canGenerateRandomExplosive(game) && this.getExplosive())
		{
			useExplosive();
			object = new Explosive(game,this.getX(),this.getY(), 10, this.getCycles(), this.cyclesToMove, this.sentido, this.live, isInFinalRow, shipsOnBorder);
			game.removeObject(this);
			game.addObject(object);
		}
			}

	public boolean receiveMissileAttack(int damage)
	{
		this.live-=damage;
		if(!this.isAlive()) this.getPoints();
		return false;
	}
	
	public void useExplosive()
	{
		this.explosive = false;
	}
	
	public boolean getExplosive()
	{
		return this.explosive;
	}
	
	public String toString() {
		String pintar;
		pintar = "C[" + this.live + "]";
		return pintar;
	}
	
	public String stringify() {
		String pintar;
		pintar = "R;" + this.x +","+ this.y +";" +this.live+";"
		+ this.cyclesToMove+";"+ this.sentido;
		return pintar;
	}
}
