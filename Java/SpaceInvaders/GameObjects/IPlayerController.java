package GameObjects;

import Exceptions.OffWorldException;
import Exceptions.ShockWaveException;
import Exceptions.ShootException;

public interface IPlayerController {
	// PLAYER ACTIONS
	public boolean move (int numCells) throws OffWorldException;
	public boolean shootLaser() throws ShootException;
	public boolean shockWave() throws ShockWaveException;
	// CALLBACKS
	public void receivePoints(int points);
	public void enableShockWave();
	public void enableMissile();
}
