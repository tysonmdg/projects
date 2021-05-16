package GameObjects;
import game.Game;


public interface IExcecuteRandomActions {
	static boolean canGenerateRandomOvni(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getOvniFrequency();
		}
	static boolean canGenerateRandomBomb(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getShootFrequency();
		}
	static boolean canGenerateRandomExplosive(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getTurnExplodeFreq();
		}

}
