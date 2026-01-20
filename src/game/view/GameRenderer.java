package game.view;

import game.Coordinate;
import game.ImageLoader;
import game.effects.Effect;
import game.enemies.Enemy;
import game.entities.Player;
import game.enums.TowerType;
import game.towers.Tower;

import java.awt.*;
import java.util.List;

public class GameRenderer {
    private final Image backdrop;

    public GameRenderer() {
        ImageLoader loader = ImageLoader.getLoader();
        backdrop = loader.getImage("resources/stars.jpg");
    }

    public void draw(Graphics g, Player player, List<Enemy> enemies, List<Tower> towers, List<Effect> effects) {
        // Draw the background image.
        g.drawImage(backdrop, 0, 0, null);

        // Draw the path
        g.setColor(new Color (0,76, 153));
        int[] xPos = new int[]{0, 64, 118, 251, 298, 344, 396, 416, 437, 459, 460, 498, 542, 600, 600, 568, 535, 509, 490, 481, 456, 414, 345, 287, 227, 98, 0};
        int[] yPos = new int[]{329, 316, 291, 189, 163, 154, 165, 186, 233, 344, 364, 415, 444, 461, 410, 396, 372, 331, 226, 195, 151, 117, 105, 117, 143, 244, 280};
        g.fillPolygon(xPos, yPos, 27);

        // Draw planet
        g.setColor(new Color(65,105,225));
        g.fillArc(550, 385, 100, 100, 90, 180);
        g.setColor(Color.GREEN);
        int[] xCor = new int[]{600, 588, 574, 566, 557, 557, 563, 572, 576, 584, 600};
        int[] yCor = new int[]{459, 464, 462, 453, 454, 448, 438, 435, 422, 414, 415};
        g.fillPolygon(xCor, yCor, 11);

        // draw all enemies
        for(Enemy e: enemies)
            e.draw(g);

        // draw all towers
        for(Tower t: towers)
            t.draw(g);

        // draw all effects
        for(Effect s: effects)
            s.draw(g);

        drawHUD(g, player);
    }

    private void drawHUD(Graphics g, Player player) {
        // draw menu bar
        g.setColor(Color.WHITE);
        g.fillRect(600, 0, 200, 800);

        // draw score & life counters
        g.setColor(Color.BLACK);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        g.drawString("Lives Remaining: " + player.getLives(), 605, 100);
        g.drawString("Money Earned: " + player.getMoney(), 605, 150);
        g.drawString("Enemies Stopped: " + player.getKills(), 605, 200);

        g.setFont(new Font("Lucidia Sans", Font.ITALIC, 28));
        g.drawString("Planet Defense", 600, 50);
        g.drawLine(600, 50, 800, 50);
        g.drawString("Towers", 640, 240);
        g.drawLine(620, 240, 780, 240);

        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));

        for (TowerType type : TowerType.values()) {
            g.setColor(Color.BLACK);
            g.drawString(type.displayName + " Cost: " + type.cost, 610, type.iconY + 120);

            g.setColor(new Color(224, 224, 224));
            g.fillRect(650, type.iconY, 100, 100);

            Tower menuTower = type.factory.apply(new Coordinate(700, type.iconY + 50));
            menuTower.draw(g);
        }
    }

    public void drawGameOver(Graphics g, boolean isWon) {
        if (!isWon) {
             ImageLoader loader = ImageLoader.getLoader();
             Image endGame = loader.getImage("resources/game_over.png");
             g.drawImage(endGame, 0, 0, null);
        } else {
            g.setFont(new Font("Braggadocio", Font.ITALIC, 90));
            g.drawString("You Win!!!", 10, 250);
        }
    }
}
