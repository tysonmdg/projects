package commands;
import Exceptions.CommandParseException;
import game.Game;

public class ListCommand extends Command {

	protected final static String name = "list";
	protected final static String shortcut = "l";
	private final static String details = "[l]ist";
	private final static String help = "Prints the list of available ships.";
	
	public ListCommand() {
		super(name, shortcut, details, help);
	}


	public boolean execute(Game game) {
		System.out.println(game.infoToString());
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
