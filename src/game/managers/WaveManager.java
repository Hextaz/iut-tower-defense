package game.managers;

import game.PathPoints;
import game.enemies.Alien;
import game.enemies.Asteroid;
import game.enemies.Comet;
import game.enemies.Enemy;

import java.util.List;

public class WaveManager {
    private final PathPoints spawnPath;
    private int frameCounter;

    public WaveManager(PathPoints spawnPath) {
        this.spawnPath = spawnPath;
        this.frameCounter = 0;
    }

    public void update(List<Enemy> enemies) {
        frameCounter++;
        generateEnemies(enemies);
    }

    private void generateEnemies(List<Enemy> enemies) {
        // adds enemies to list dependent on how many frames have passed
        if(frameCounter % 30 == 0)								// slow
        {
            enemies.add(new Asteroid(spawnPath.getStart()));
        }
        else if(frameCounter % 25 == 0 && frameCounter >= 50)	// slow
        {
            enemies.add(new Asteroid(spawnPath.getStart()));
        }
        else if(frameCounter % 20 == 0 && frameCounter >= 100)	// medium
        {
            enemies.add(new Asteroid(spawnPath.getStart()));
            enemies.add(new Alien(spawnPath.getStart()));
        }
        else if(frameCounter % 15 == 0 && frameCounter >= 150)	// medium
        {
            enemies.add(new Asteroid(spawnPath.getStart()));
            enemies.add(new Alien(spawnPath.getStart()));
        }
        else if(frameCounter % 10 == 0 && frameCounter >= 200)	// fast
        {
            enemies.add(new Asteroid(spawnPath.getStart()));
            enemies.add(new Alien(spawnPath.getStart()));
            enemies.add(new Comet(spawnPath.getStart()));
        }
        else if(frameCounter % 5 == 0 && frameCounter >= 250)	// fast
        {
            enemies.add(new Asteroid(spawnPath.getStart()));
            enemies.add(new Alien(spawnPath.getStart()));
            enemies.add(new Comet(spawnPath.getStart()));
        }
    }
}
