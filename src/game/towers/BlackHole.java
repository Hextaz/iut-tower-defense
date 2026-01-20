package game.towers;

import game.*;
import game.effects.StarDust;

/**
 * This class creates a single blackhole tower
 */
public class BlackHole extends Tower
{
	/**
	 * Constructor
	 */
	public BlackHole(Coordinate pos)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.tower = loader.getImage("resources/blackhole.png");
		this.position = pos;
		this.anchorX = -40;
		this.anchorY = -40;
	}
	
	/**
	 * 
	 */
	@Override
	protected double getFireRate() {
		return 1.0;
	}

	@Override
	protected double getRange() {
		return 80;
	}

	@Override
	protected void createEffect(Game game, Coordinate pos, Coordinate enemyPos) {
		StarDust stardust = new StarDust(pos, enemyPos);
		game.getEffects().add(stardust);
	}
}
