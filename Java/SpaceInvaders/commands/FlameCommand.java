package commands;

import Exceptions.CommandExecuteException;
import Exceptions.CommandParseException;
import Exceptions.ShootException;
import game.Game;

public class FlameCommand extends Command{
	protected final static String name = "flamethrower";
	protected final static String shortcut = "f";
	private final static String details = "[f]lamethrower";
	private final static String help = " UCM-Ship destroys every alien on his column.";
	public FlameCommand() {
		super(name, shortcut, details, help);
		
	}


	public boolean execute(Game game) throws CommandExecuteException {
		try {
			game.useFlame();
		}
		catch(ShootException ex)
		{
			throw new CommandExecuteException("Cannot shoot: " + ex.getMessage(), this);
		}
		
		return true;
	}

	
	public Command parse(String[] commandWords) throws CommandParseException {
	
		if(matchCommandName(commandWords[0]))
		{
			if(commandWords.length > 1) 
			{
					throw new CommandParseException(incorrectArgsMsg, this);
			}
			return this;
		}
		else return null;
	}

}
