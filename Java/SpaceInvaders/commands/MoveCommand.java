package commands;
import Exceptions.CommandExecuteException;
import Exceptions.CommandParseException;
import Exceptions.OffWorldException;
import game.Game;
public class MoveCommand extends Command
{
	private int pas;
	protected final static String name = "move";
	protected final static String shortcut = "m";
	private final static String details = "[m]ove";
	private final static String help = "<left|right><1|2>: Moves UCM-Ship to the indicated direction.";
	
	public MoveCommand() {
		super(name, shortcut, details, help);
	}


	public boolean execute(Game game) throws CommandExecuteException{
		try {
			game.move(pas);
		}catch(OffWorldException ex) 
		{
			throw new CommandExecuteException("Cannot move: " + ex.getMessage(), this);
		}
		return true;
	}

	
	public Command parse(String[] commandWords) throws CommandParseException {
		if(matchCommandName(commandWords[0]))
		{
			if(commandWords.length == 3)
			{
				if(Integer.decode(commandWords[2]) < 3 && Integer.decode(commandWords[2]) > 0 )
				{
					if(!commandWords[1].equalsIgnoreCase("left") && !commandWords[1].equalsIgnoreCase("right") ) 
						new CommandParseException("Invalid second argument", this);
					if(commandWords[1].equalsIgnoreCase("LEFT")) 
					{
						pas = Integer.decode(commandWords[2]) * -1;
					}
					else if(commandWords[1].equalsIgnoreCase("RIGHT")){
						pas = Integer.decode(commandWords[2]);	
					}
					
					
					}
				else throw new CommandParseException(incorrectArgsMsg, this);
				return this;
			}
			else throw new CommandParseException(incorrectNumArgsMsg, this);
			
		}
		else return null;
		
	}

}
