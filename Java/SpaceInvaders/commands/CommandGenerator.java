package commands;

import Exceptions.CommandParseException;

public class CommandGenerator {
	
	private static Command comando;
	private static Command[] availableCommands = {
			new MoveCommand(),
			new ShockwaveCommand(),
			new BuyMissile(),
			new ShootCommand(),
			new FlameCommand(),
			new ListCommand(),
			new ListPrintersCommand(),
			new HelpCommand(),
			new ResetCommand(),
			new UpdateCommand(),
			new StringifyCommand(),
			new SaveCommand(),
			new ExitCommand()
		};

	public static Command parseCommand(String[] commandWords) throws CommandParseException
	{
		comando = null;
		for(Command c : availableCommands) {
			if(comando == null) comando = c.parse(commandWords);
		}
		return comando;
	}
	
	public static String commandHelp()
	{
		String help = "Command > help" + "\n";
		for(Command c: availableCommands)
		{
			help = help + c.helpText();
		}
		return help;
	}
	
}


