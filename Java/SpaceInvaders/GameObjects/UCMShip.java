package GameObjects;
import Exceptions.OffWorldException;
import game.Game;

public class UCMShip extends Ship{
	private int points;
	private Boolean hasShockWave;
	private Boolean canShootLaser;
	private int nSuper;
	private int nFlame;
	private Boolean shieldAvailable;
	
	public UCMShip(Game game, int x, int y) {
		super(game, x, y , 3);
		this.points = 0;
		this.hasShockWave = false;
		this.canShootLaser = true;
		this.nSuper = 0;
		this.nFlame = 0;
		this.shieldAvailable = false;
	}

	public String stateToString() {
		String cadena;
		cadena = this.toString() + " Harm: 1 - Shield: " + this.getLive() + "\n";
		return cadena;
	}
	
	public void activateSW()
	{
		this.hasShockWave = true;
	}
	
	public void useSW()
	{
		this.hasShockWave = false;
	}
	
	public Boolean availableSW()
	{
		return hasShockWave;
	}
	
	public void activateMissile()
	{
		this.canShootLaser = true;
	}
	
	public void useMissile()
	{
		this.canShootLaser = false;
	}
	
	public Boolean availableLaser()
	{
		return canShootLaser;
	}
	
	public void buyShield()
	{
		this.shieldAvailable = true;
		this.points-=10;
	}
	
	
	public Boolean shield()
	{
		return shieldAvailable;
	}
	
	public void activateShield()
	{
		this.shieldAvailable = true;
	}
	
	public void useShield()
	{
		this.shieldAvailable = false;
	}
	
	public void buyFlame()
	{
		this.nFlame++;
		this.points-=30;
	}
	
	public void useFlame()
	{
		this.nFlame--;
	}
	
	public boolean availableFlame()
	{
		return this.nFlame > 0;
	}
	
	public void buySuper()
	{
		this.nSuper++;
		this.points-=20;
	}
	
	public void useSuper()
	{
		this.nSuper--;
	}
	
	public boolean availableSuper()
	{
		return this.nSuper > 0;
	}
	
	public int getNSuper()
	{
		return this.nSuper;
	}
	
	public void move(int n) throws OffWorldException {
		if(	this.y + n >= 0 && this.y + n <= 8)
		{
			this.y += n;
		}
		else throw new OffWorldException("ship too near border");
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public void addPoints(int puntos)
	{
		this.points += puntos;
	}
	
	public boolean receiveBombAttack(int damage)
	{
		if(shieldAvailable)
		{
			useShield();
		}
		else this.live-=damage;
		return true;
	}
	
	public String toString() {
		String pintar;
		if(this.getLive() == 0) pintar = "!xx!";
		else pintar = "^__^";
		return pintar;
	}
	
	public String stringify() {
		String pintar;
		pintar = "P;" + this.x +","+ this.y +";" +this.live+";"
		+ this.points+";"+ this.hasShockWave+";"+ this.canShootLaser;
		return pintar;
	}
}
