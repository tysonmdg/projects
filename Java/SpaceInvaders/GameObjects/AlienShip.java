package GameObjects;

import game.Game;


public class AlienShip extends EnemyShip {

	protected static int remainingAliens = 0;
	protected static boolean isInFinalRow;
	protected static int shipsOnBorder;
	protected int cyclesToMove;
	protected int sentido;
	private int c;
	
	public AlienShip(Game game, int x, int y, int live, int puntos,int cycles) {
		super(game, x, y, live, puntos);
		remainingAliens++;
		shipsOnBorder = 0;
		this.cyclesToMove = cycles;
		this.sentido = -1;
		isInFinalRow = false;
		this.c = cycles;
	}
	
	protected void move(int n)
	{
		this.y += n;
	}
	
	public void move()
	{
		if(cyclesToMove == 0)
		{
			this.cyclesToMove = c;
			this.move(sentido);
		
			if(this.getY() == 0 || this.getY() == 8) shipsOnBorder = remainingAliens;
		}
		else if(shipsOnBorder > 0)
		{
			this.x++;
			this.sentido*=-1;
			shipsOnBorder--;
	
		}
		else cyclesToMove--;
		if(this.getX() == 7) isInFinalRow = true;
	}

	public static void resetRemaining()
	{
		remainingAliens = 0;
	}
	public int getCycles()
	{
		return this.c;
	}
	public static boolean haveLanded() {
		return isInFinalRow;
	}

	public static boolean allDead() {
		if(remainingAliens == 0) return true;
		else return false;
	}

	public void onDelete()
	{
		remainingAliens--;
	}
	
	public static String getRemainingAliens() {
		
		return remainingAliens + " ";
	}
	
	
	public boolean receiveExplosiveAttack(int damage)
	{
		this.live-=damage;
		if(!this.isAlive()) this.getPoints();
		return false;
	}
	
	public boolean receiveShockWaveAttack(int damage)
	{
		this.live-=damage;
		if(!this.isAlive()) this.getPoints();
		return true;
	}
	
	public boolean receiveFlameAttack(int damage)
	{
		this.live-=damage;
		if(!this.isAlive()) this.getPoints();
		return true;
	}

}
