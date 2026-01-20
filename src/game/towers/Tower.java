package game.towers;

import game.Coordinate;
import game.Game;
import game.enemies.Enemy;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

/**
 * This is an abstract superclass for a tower in the game
 */
abstract public class Tower 
{
	/* instance variables */
	protected Coordinate position;	// holds position of tower
	protected Image tower; 			// holds tower image
	protected int anchorX;			// shifts X coordinate
	protected int anchorY;			// shifts Y coordinate
	protected double timeSinceLastFire;// time since last effect was fired
	
	public void draw(Graphics g)
	{
		// Draws tower object to location specified by user
		g.drawImage(tower, position.getX() + anchorX, position.getY() + anchorY, null);
		
		// Draws dot on Enemy's (x, y) coordinates
		//g.setColor(Color.WHITE);
		//g.fillOval(position.getX(), position.getY(), 5, 5);	
	}
	/**
	 * 
	 * @param c
	 */
	public void setPosition(Coordinate c)
	{
		position = c;
	}
	
    public void interact(Game game, double deltaTime)
    {
        // tracks time that effect has existed
        timeSinceLastFire += deltaTime;

        // if time less than fire rate, don't interact
        if(timeSinceLastFire < getFireRate())
            return;

        List<Enemy> enemies = game.getEnemies(); // new list of enemies

        // Gives position of an enemy in enemy list
        for(Enemy e: enemies)
        {
            // holds position of enemy
            Coordinate enemyPos = e.getPosition().getCoordinate();

            // Compute distance of enemy to tower
            double dx, dy, dist;	// change in x, y, and total distance

            // calculates change in x and y position
            dx = enemyPos.x - position.x; // x position of enemy - tower
            dy = enemyPos.y - position.y; // y position of enemy - tower

            // use Pythagorean theorem to calculate distance
            dist = Math.sqrt((dx*dx) + (dy*dy));

            // holds position of effect
            Coordinate pos = new Coordinate(position.x, position.y);

            // if enemy is in range, fire effect
            if(dist < getRange())
            {
                createEffect(game, pos, enemyPos);
                timeSinceLastFire = 0;
                return;
            }
        }
    }

    protected abstract double getFireRate();
    protected abstract double getRange();
    protected abstract void createEffect(Game game, Coordinate pos, Coordinate enemyPos);
}
