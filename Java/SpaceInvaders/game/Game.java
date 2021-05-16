package game;
import java.util.Random;

import Exceptions.BuySuperException;
import Exceptions.OffWorldException;
import Exceptions.ShockWaveException;
import Exceptions.ShootException;
import utils.Level;
import GameObjects.AlienShip;
import GameObjects.BoardInitializer;
import GameObjects.GameObject;
import GameObjects.GameObjectBoard;
import GameObjects.IPlayerController;
import GameObjects.Shockwave;
import GameObjects.UCMMissile;
import GameObjects.UCMShip;
import GameObjects.UCMSuperM;
import GameObjects.flameThrower;

public class Game implements IPlayerController {
	public final static int DIM_X = 8;
	public final static int DIM_Y = 9;
	private int currentCycle;
	private Random rand;
	private Level level;
	GameObjectBoard board;
	private UCMShip player;
	private boolean doExit;
	private BoardInitializer initializer;
	public Game (Level level, Random random){
		this. rand = random;
		this.level = level;
		initializer = new BoardInitializer();
		initGame();
	}
	
	public void initGame () {
		currentCycle = 0;
		board = initializer.initialize(this, level);
		player = new UCMShip(this, DIM_X -1, DIM_Y/2);
		board.add(player);
	}
	
	public Random getRandom() {
		return rand;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void dibujaTablero()
	{
		System.out.println("Life: " + player.getLive());
		System.out.println("Number of cycles: " + this.currentCycle);
		System.out.println("Points: " + player.getPoints());
		System.out.println("Remaining aliens: " + AlienShip.getRemainingAliens());
		System.out.println("Number of supermissiles: " + player.getNSuper());
		if(player.availableSW()) System.out.println("Shockwave: YES");
		else System.out.println("Shockwave: NO");
		if(player.shield())System.out.println("Shield: YES");
		else System.out.println("Shield: NO");
	}
	
	public void reset() {
		initGame();
	}
	
	public void addObject(GameObject object) {
		board.add(object);
	}

	public void removeObject(GameObject object)
	{
		board.remove(object);
	}
	
	public String positionToString(int x, int y) {
		return board.toString(x, y);
	}
	
	public boolean isFinished() {
		return playerWin() || aliensWin() || doExit;
	}
	public boolean aliensWin() {
		return !player.isAlive() || AlienShip.haveLanded() ;
	}
	
	private boolean playerWin () {
		return AlienShip.allDead();
	}
	
	public void update() {
		board.computerAction();
		board.update();
		currentCycle += 1;
	}
	
	public boolean isOnBoard(int x, int y) {
		return x >= 0 && y >= 0 && x < DIM_X && y < DIM_Y;
	}
		
	public void exit() {
		doExit = true;
	}
		
	public String infoToString() {
		return "Cycles: " + currentCycle + "\n" +
		player.stateToString() +
		"Remaining aliens: " + (AlienShip.getRemainingAliens());
	}
		
	public String getWinnerMessage () {
		if (playerWin()) return "Player win!";
		else if (aliensWin()) return "Aliens win!";
		else if (doExit) return "Player exits the game";
		else return "This should not happen";
	}
	public String stringify()
	{
		String text = "- Space Invaders v2 -" + "\n \n";
		text += "G: " + currentCycle + "\n";
		text += "L: " + this.level + "\n";
		return text + board.stringify();
	}

	@Override
	public boolean move(int numCells) throws OffWorldException {
		player.move(numCells);	
		return true;
	}

	@Override
	public boolean shootLaser() throws ShootException {
		GameObject object;
		if(player.availableLaser())
		{
			object = new UCMMissile(this,player.getX(), player.getY(), 1);
			addObject(object);
			player.useMissile();
		}
		else throw new ShootException("missile already exist on board");
		return true;
	}

	public boolean shootSuper() throws ShootException {
		GameObject object;
		if(player.availableSuper())
		{
			if(player.availableLaser())
			{
				object = new UCMSuperM(this,player.getX(), player.getY(), 2);
				addObject(object);
				player.useSuper();
				player.useMissile();
			}
			else throw new ShootException("missile already exist on board");
		}
		else throw new ShootException("unavailable supermissile");
		return true;
	}
	
	public boolean useFlame() throws ShootException
	{
		GameObject object;
		if(player.availableFlame())
		{
			object = new flameThrower(this,player);
			addObject(object);
			player.useFlame();
			board.flameThrower(object);
		}
		else throw new ShootException(" unavailable flamethrower");
		
		return true;
	}
	
	@Override
	public boolean shockWave() throws ShockWaveException {
		GameObject object;
		if(player.availableSW())
		{
			object = new Shockwave(this);
			addObject(object);
			board.ShockWave(object);
			player.useSW();
		}
		else 
			{
			throw new ShockWaveException("unavailable Shockwave");
			}
		return true;
	}

	public void Explosive(GameObject object) {
		board.Explosive(object);
	}
	@Override
	public void receivePoints(int points) {
		player.addPoints(points);
	}

	@Override
	public void enableShockWave() {
		player.activateSW();
	}

	@Override
	public void enableMissile() {
		player.activateMissile();
		
	}
	
	public void buySuper() throws BuySuperException
	{
		if(player.getPoints() >= 20)
		{
			
			player.buySuper();
		}
		else throw new BuySuperException("insuficient points to buy a supermissile");
	}
	
	public void buyFlame() throws BuySuperException
	{
		if(player.getPoints() >= 30)
		{
			
			player.buyFlame();
		}
		else throw new BuySuperException("insuficient points to buy a flamethrower");
	}
	
	public void buyShield() throws BuySuperException
	{
		if(player.getPoints() >= 10)
		{
			player.buyShield();
		}
		else throw new BuySuperException("insuficient points to buy shield");
	}
	
	public String toStringObjectAt(int i, int j) {
		return board.toString(i, j);
	}
}
