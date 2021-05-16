package commands;

import Exceptions.CommandParseException;
import game.Game;
import utils.GamePrinter;
import utils.PrinterTypes;

public class StringifyCommand extends Command {
	protected final static String name = "stringify";
	protected final static String shortcut = "g";
	private final static String details = "strin[g]ify";
	private final static String help = "Changes the format. ";
	
	public StringifyCommand() {
		super(name, shortcut, details, help);
	}


	public boolean execute(Game game) {
		GamePrinter prin = PrinterTypes.STRINGIFIER.getObject(game);
		System.out.println(prin);
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
