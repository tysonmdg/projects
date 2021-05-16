package commands;

import Exceptions.BuySuperException;
import Exceptions.CommandExecuteException;
import Exceptions.CommandParseException;
import game.Game;

public class BuyMissile extends Command {
	protected final static String name = "buy";
	protected final static String shortcut = "b";
	private final static String details = "[b]uy";
	private final static String help = "Buy an item for points (shield(10), supermissile(20) or flamethrower(30)).";
	private String objeto;
	public BuyMissile() {
		super(name, shortcut, details, help);
		this.objeto = "";
	}


	public boolean execute(Game game) throws CommandExecuteException {
		try
		{
			if(objeto.equalsIgnoreCase("supermissile")) game.buySuper();
			else if(objeto.equalsIgnoreCase("flamethrower")) game.buyFlame();
			else game.buyShield();
		}
		catch(BuySuperException ex)
		{
			throw new CommandExecuteException("Cannot buy supermissile: " + ex.getMessage(), this);
		}
		return true;
	}

	
	public Command parse(String[] commandWords) throws CommandParseException {
	
		if(matchCommandName(commandWords[0]))
			{
				if(commandWords.length > 1) 
				{
					if(commandWords.length > 2) throw new CommandParseException(incorrectArgsMsg,this);
					if(commandWords.length == 2 && (commandWords[1].equalsIgnoreCase("supermissile") || commandWords[1].equalsIgnoreCase("flamethrower") || commandWords[1].equalsIgnoreCase("shield")))
						this.objeto = commandWords[1];
					else throw new CommandParseException("Incorrect command name", this);
				}
	
				return this;
			}
		else return null;
	}

}
