package GameObjects;

import game.Game;
import utils.Level;

public class BoardInitializer {
	private Level level;
	private GameObjectBoard board;
	private Game game;
	
	public GameObjectBoard initialize(Game game, Level level) {
		this.level = level;
		this.game = game;
		board = new GameObjectBoard(Game.DIM_X, Game.DIM_Y);
		initRemaining();
		initializeOvni();
		initializeRegularAliens();
		initializeDestroyerAliens();
		return board;
	}
	
	private void initializeOvni () {
		board.add(new Ovni(game,0,9));
	} 
	
	private void initRemaining()
	{
		AlienShip.resetRemaining();
	}
	
	private void initializeRegularAliens () {
		int fila = 1, col = 3;
		int fin = col + level.getNumRegularAliensPerRow();
		for(int i = 0; i < level.getNumRegularAliens();i++)
		{
			if(fin == col )
			{
				col = 3;
				fila++;
			}
			board.add(new RegularShip(game,fila,col,level.getNumCyclesToMoveOneCell()));
			col++;
		}
	}
	private void initializeDestroyerAliens () {
		int fila = level.getNumRowsOfRegularAliens() + 1;
		int col;
		
		if(level.getNumDestroyerAliensPerRow() == 2) col = 4;
		else col = 3;
		
		for(int i = 0; i < level.getNumDestroyerAliens();i++)
		{
			board.add(new DestroyerShip(game,fila,col,level.getNumCyclesToMoveOneCell()));
			col++;
		}
	}
	
	
}
