package commands;
import game.Game;
import utils.GamePrinter;
import utils.Level;
import utils.PrinterTypes;

import java.util.Scanner;

import Exceptions.CommandExecuteException;
import Exceptions.CommandParseException;

public class Controller {
		private Game game;
		private Scanner in;
		private GamePrinter prin;
		
		public Controller(Game game, Scanner in)
		{
			this.game = game;
			this.in = in;
			this.prin = PrinterTypes.BOARDPRINTER.getObject(game);
		}
		public void run(Level l) {

			String[] opcion; 
			Boolean fin = false;
			game.dibujaTablero();
			System.out.println(prin);
			
			while(!fin)
			{
				opcion = in.nextLine().toLowerCase().trim().split("\\s+");
				try
				{
					Command command = CommandGenerator.parseCommand(opcion);
					
					if(command != null)
					{
						System.out.println("Command > " + command.name);
						if(command.execute(game)) 
							{
							game.update();
							game.dibujaTablero();
							System.out.println(prin);
							}
					}
					else System.out.format("Invalid instruction");
				} catch(CommandParseException | CommandExecuteException ex)
				{
					System.out.format(ex.getMessage() + " %n %n");
				}
				
				if(game.isFinished()) 
					{
						System.out.println(game.getWinnerMessage());
						fin = true;
					}
			}
		
		}
		
	}


