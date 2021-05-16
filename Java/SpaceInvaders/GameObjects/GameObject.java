package GameObjects;
import game.Game;

public abstract class GameObject implements IAttack {
	protected int x;
	protected int y;
	protected int live;
	protected Game game; 
	
	public GameObject( Game game, int x, int y, int live) {
		this. x = x;
		this. y = y;
		this. game = game;
		this.live = live;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isAlive() {
		return this.live > 0;
	}
	
	public int getLive() {
		return this.live;
	}
	
	public boolean isOnPosition(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	public void getDamage (int damage) {
		this.live = damage >= this.live ? 0 : this.live - damage;
	}
	
	public boolean isOut() {
		return !game.isOnBoard(x, y);
	}
	
	public abstract void computerAction();
	public abstract void onDelete();
	public abstract void move();
	public abstract String toString();
	public abstract String stringify();

}
