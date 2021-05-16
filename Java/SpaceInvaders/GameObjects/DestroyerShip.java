package GameObjects;
import game.Game;

public class DestroyerShip extends AlienShip {
	
	private boolean canShootBomb;
	
	public DestroyerShip(Game game, int x, int y, int cycles) {
		super(game, x, y, 1, 10, cycles);
		canShootBomb = true;
	}

	
	public void computerAction()
	{
		GameObject object;
		if(IExcecuteRandomActions.canGenerateRandomBomb(game) && this.getBomb())
		{
			this.useBomb();
			object = new Bomb(game,this.getX(),this.getY(), this);
			game.addObject(object);
		}
	}
	
	public void activateBomb()
	{
		this.canShootBomb = true;
	}
	
	public void useBomb()
	{
		this.canShootBomb = false;
	}
	
	public boolean getBomb()
	{
		return canShootBomb;
	}
	
	public boolean receiveMissileAttack(int damage)
	{
		this.live-=damage;
		if(!this.isAlive()) this.getPoints();
		return false;
	}
	
	public String toString() {
		String pintar;
		pintar = "D[" + this.live + "]";
		return pintar;
	}
	
	public String stringify() {
		String pintar;
		pintar = "D;" + this.x +","+ this.y +";" +this.live+";"
		+ this.cyclesToMove+";"+ this.sentido;
		return pintar;
	}
}
