package commands;
import Exceptions.CommandParseException;
import game.Game;

public class ResetCommand extends Command {
	
	protected final static String name = "reset";
	protected final static String shortcut = "r";
	private final static String details = "[r]eset";
	private final static String help = " Starts a new game.";
	
	public ResetCommand() {
		super(name, shortcut, details, help);
	}


	public boolean execute(Game game) {
		game.reset();
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
