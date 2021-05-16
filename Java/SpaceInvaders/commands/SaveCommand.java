package commands;

import java.io.*;

import Exceptions.CommandParseException;
import game.Game;

public class SaveCommand extends Command{
	
	private String nFile = "game1";
	protected final static String name = "save";
	protected final static String shortcut = "v";
	private final static String details = "sa[v]e";
	private final static String help = "Saves the current state of the game.";
	
	public SaveCommand() {
		super(name, shortcut, details, help);
	
	}


	public boolean execute(Game game) {
		String stringify = game.stringify();
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter(nFile));
			file.write(stringify);
			file.close();
			System.out.println("Game successfully saved in file <" + nFile 
					+ ">. Use the load command to reload it");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return false;
	}

	
	public Command parse(String[] commandWords) throws CommandParseException {
		if(matchCommandName(commandWords[0]))
		{
			if(commandWords.length == 2)
				this.nFile = commandWords[1] + ".dat";
			else throw new CommandParseException(incorrectArgsMsg,this);
	
			return this;
		}
		else return null;
	}
}
