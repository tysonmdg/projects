package commands;
import Exceptions.CommandExecuteException;
import Exceptions.CommandParseException;
import Exceptions.ShootException;
import game.Game;

public class ShootCommand extends Command{
	protected final static String name = "shoot";
	protected final static String shortcut = "s";
	private final static String details = "[s]hoot";
	private final static String help = "<supermissile> UCM-Ship launches a missile.";
	private String superM;
	public ShootCommand() {
		super(name, shortcut, details, help);
		this.superM = " ";
	}


	public boolean execute(Game game) throws CommandExecuteException {
		try {
			if(superM.equalsIgnoreCase("supermissile"))
			{
				superM = " ";
				game.shootSuper();
			}
			else
			{
				game.shootLaser();
			}
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
				if(commandWords.length > 2)
					throw new CommandParseException(incorrectArgsMsg,this);
				if(commandWords.length == 2 && commandWords[1].equalsIgnoreCase("supermissile"))
					this.superM = commandWords[1];
				else 
					throw new CommandParseException("Incorrect command name", this);
			}
			return this;
		}
		else return null;
	}

}
