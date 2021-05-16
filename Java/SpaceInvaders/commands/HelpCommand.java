package commands;
import Exceptions.CommandParseException;
import game.Game;

public class HelpCommand extends Command {
	
	protected final static String name = "help";
	protected final static String shortcut = "h";
	private final static String details = "[h]elp";
	private final static String help = "Prints this help message.";
	
	public HelpCommand() {
		super(name, shortcut, details, help);
	
	}


	public boolean execute(Game game) {
		System.out.println(CommandGenerator.commandHelp());
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
