package utils;
import game.Game;

public abstract class GamePrinter {
		
	Game game;
	
	public Game setGame(Game game)
	{
		return this.game = game;
	}
		
	public String toString(Game game)
	{
		return null;
	}
	}

