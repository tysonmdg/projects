package commands;
import Exceptions.CommandExecuteException;
import Exceptions.CommandParseException;
import Exceptions.ShockWaveException;
import game.Game;

public class ShockwaveCommand extends Command {
	
	protected final static String name = "shockwave";
	protected final static String shortcut = "w";
	private final static String details = "shock[w]ave";
	private final static String help = "UCM-Ship releases a shock wave.";
	
	public ShockwaveCommand() {
		super(name, shortcut, details, help);
	}


	public boolean execute(Game game) throws CommandExecuteException {
		try {
			game.shockWave();
		}
		catch(ShockWaveException ex)
		{
			throw new CommandExecuteException("Cannot release shockwave: " + ex.getMessage(), this);
		}
		return true;
	}

	
	public Command parse(String[] commandWords) throws CommandParseException {
		if(matchCommandName(commandWords[0]))
		{
			if(commandWords.length > 1) 
				throw new CommandParseException(incorrectArgsMsg,this);
			return this;
		}
	else return null;
	}

}
