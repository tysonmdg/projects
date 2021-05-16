package Exceptions;

import commands.Command;

public class CommandExecuteException extends Exception{
	
	private String text;
	private Command comando;
	
	public CommandExecuteException(String text, Command c)
	{
		this.text = text;
		this.comando = c;
	}

	public String getMessage()
	{
		String text = "";
		text += "Failed to " + this.comando.getName() + "\n";
		text += "Cause of Exception: " +"\n" + this.comando +": " + this.text;
		return text;
	}

}
