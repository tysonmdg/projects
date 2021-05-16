package commands;

import Exceptions.CommandParseException;
import game.Game;
import utils.PrinterTypes;

public class ListPrintersCommand extends Command{
	protected final static String name = "PrinterTypes";
	protected final static String shortcut = "P";
	private final static String details = "[p]rinterTypes";
	private final static String help = "Show a list of the printer types. ";
	
	public ListPrintersCommand() {
		super(name, shortcut, details, help);
	}


	public boolean execute(Game game) {
		System.out.println("Command > listPrinters");
		System.out.println(PrinterTypes.printerHelp(game));
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
