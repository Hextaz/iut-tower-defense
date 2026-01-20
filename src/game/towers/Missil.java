package game.towers;

import game.Coordinate;
import game.enemies.Enemy;
import game.Game;
import game.ImageLoader;
import game.effects.Bullet;

import java.awt.*;
import java.util.List;

public class Missil extends Tower {

    public Missil(Coordinate pos) {
        ImageLoader loader = ImageLoader.getLoader();
        Image original = loader.getImage("resources/missil.png");
        this.tower = original.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        this.position = pos;
        this.anchorX = -35;
        this.anchorY = -35;
    }

    @Override
    public void interact(Game game, double deltaTime) {
        // tracks time that effect has existed
        timeSinceLastFire += deltaTime;

        // if time less than 1.5 seconds, don't interact
        if(timeSinceLastFire < 1)
            return;

        List<Enemy> enemies = game.enemies; // new list of enemies

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

            // if enemy is in range, fire salt
            if(dist < 70)
            {	Bullet bullet = new Bullet(pos, enemyPos);
                game.effects.add(bullet);
                timeSinceLastFire = 0;
                return;
            }
        }
    }
}
