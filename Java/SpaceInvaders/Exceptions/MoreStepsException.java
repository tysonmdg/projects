package Exceptions;

public class MoreStepsException extends Exception {

	public MoreStepsException()
	{
		super("trying to move more than 2 steps");
	}
}
