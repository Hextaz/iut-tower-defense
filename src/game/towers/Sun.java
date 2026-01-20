package game.towers;

import game.Coordinate;
import game.Game;
import game.ImageLoader;
import game.effects.SunSpot;

/**
 * This class creates a single sun tower
 */
public class Sun extends Tower
{
	/**
	 * Constructor
	 */
	public Sun(Coordinate pos)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.tower = loader.getImage("resources/sun.png");
		this.position = pos;
		this.anchorX = -50;
		this.anchorY = -50;
	}

	/**
	 * 
	 */
	@Override
	protected double getFireRate() {
		return 0.2;
	}

	@Override
	protected double getRange() {
		return 100;
	}

	@Override
	protected void createEffect(Game game, Coordinate pos, Coordinate enemyPos) {
		SunSpot sunspot = new SunSpot(pos, enemyPos);
		game.getEffects().add(sunspot);
	}
}
