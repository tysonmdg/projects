package Exceptions;

import commands.Command;

public class CommandParseException extends Exception {

	private String text;
	private Command comando;
	
	public CommandParseException(String text, Command c)
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
