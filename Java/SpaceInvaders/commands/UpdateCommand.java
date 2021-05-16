package commands;
import Exceptions.CommandParseException;
import game.Game;

public class UpdateCommand extends Command {
	
	protected final static String name = "none";
	protected final static String shortcut = "n";
	private final static String details = "[n]one";
	private final static String help = "Skips one cycle.";
	
	public UpdateCommand() {
		super(name, shortcut, details, help);
	}


	public boolean execute(Game game) {
		
		return true;
	}

	
	public Command parse(String[] commandWords) throws CommandParseException {
		if(matchCommandName(commandWords[0]) || commandWords[0].isEmpty())
		{
			if(commandWords.length > 1) 
				throw new CommandParseException(incorrectArgsMsg,this);
			return this;
		}
	else return null;
	}


}
