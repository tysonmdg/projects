package commands;
import Exceptions.CommandParseException;
import game.Game;
public class ExitCommand extends Command {
	
	protected final static String name = "exit";
	protected final static String shortcut = "e";
	private final static String details = "[e]xit";
	private final static String help = "Terminates the program.";
	
	public ExitCommand() {
		super(name, shortcut, details, help);
	}


	public boolean execute(Game game) {
		game.exit();
		return false;
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
