package game;

public class Bullet extends Effect {
    public Bullet(Coordinate pos, Coordinate target) {
        // Loads bullet image
        ImageLoader loader = ImageLoader.getLoader();
        this.picture = loader.getImage("resources/bullet.png");

        // X and Y position of Effect
        this.posX = pos.x;
        this.posY = pos.y;

        // X and Y position of target enemy
        this.velocityX = target.x - this.posX;
        this.velocityY = target.y - this.posY;

        // Sets time to 0
        this.ageInSeconds = 0;
    }
}
