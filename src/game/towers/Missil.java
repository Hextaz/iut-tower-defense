package game.towers;

import game.Coordinate;
import game.Game;
import game.ImageLoader;
import game.effects.Bullet;

import java.awt.*;

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
    protected double getFireRate() {
        return 1.0;
    }

    @Override
    protected double getRange() {
        return 70;
    }

    @Override
    protected void createEffect(Game game, Coordinate pos, Coordinate enemyPos) {
        Bullet bullet = new Bullet(pos, enemyPos);
        game.getEffects().add(bullet);
    }
}
