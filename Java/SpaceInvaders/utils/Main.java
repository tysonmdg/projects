package utils;
import java.util.Random;
import game.Game;
import java.util.Scanner;
import commands.Controller;
import Exceptions.MainException;

public class Main {
	
	public static void main(String[] args) throws MainException {
		String levelStr;
		Level l = null;
		int seed;
		Random rand;
		
		
		
		if(args.length > 0 && args.length <= 2)
		{
			levelStr = args[0].toUpperCase();
		
			if(!levelStr.equals("EASY") && !levelStr.equals("HARD") && !levelStr.equals("INSANE"))
			{
				throw new MainException(showUsage()+ ": level must be one of: EASY, HARD, INSANE");
			}
			else
			{
				if(levelStr.equals("EASY")) l = Level.EASY;
				else if(levelStr.equals("HARD")) l = Level.HARD;
				else l = Level.INSANE;
			
			}
	
			if(args.length >1) {
				seed = Integer.parseInt(args[1]);
				rand = new Random(seed);
			}
			else 
				rand = new Random();
			
			Scanner scanner = new Scanner(System.in);
			Game game = new Game(l, rand);
			Controller controller = new Controller(game,scanner);
			controller.run(l);
		}
		else throw new MainException(showUsage());
	}

	private static String showUsage() {
		return "Main <EASY|HARD|INSANE> [seed]";
	}

}
