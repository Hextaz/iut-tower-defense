package game.managers;

import game.Coordinate;
import game.GamePanel;
import game.PathPoints;
import game.entities.Player;
import game.enums.TowerType;
import game.towers.Tower;

import java.awt.*;
import java.util.List;

public class TowerManager {
    private Tower towerBeingPlaced;
    private int towerBeingPlacedCost;

    public void update(GamePanel input, Player player, PathPoints path, List<Tower> towers) {
        Coordinate mouseLocation = new Coordinate(input.mouseX, input.mouseY);

        // If no tower is currently being placed, check if the user clicked on the menu
        if (towerBeingPlaced == null && input.mouseIsPressed) {
            if (input.mouseX > 650 && input.mouseX < 750) {
                for (TowerType type : TowerType.values()) {
                    if (input.mouseY > type.iconY && input.mouseY < type.iconY + 100) {
                        if (player.getMoney() >= type.cost) {
                            towerBeingPlaced = type.factory.apply(mouseLocation);
                            towerBeingPlacedCost = type.cost;
                        }
                        break;
                    }
                }
            }
        }
        else if (towerBeingPlaced != null)
        {
            // If we are carrying a tower, check if we can place it on the map
            if (input.mouseX > 0 && input.mouseX < 600 &&
                input.mouseY > 0 && input.mouseY < 600 &&
                input.mouseIsPressed &&
                path.distanceToPath(input.mouseX, input.mouseY) > 60)
            {
                towerBeingPlaced.setPosition(mouseLocation);
                towers.add(towerBeingPlaced);
                player.spendMoney(towerBeingPlacedCost);
                towerBeingPlaced = null;
            }
            else
            {
                // Just update the position of the tower being dragged
                towerBeingPlaced.setPosition(mouseLocation);
            }
        }
    }

    public void draw(Graphics g) {
        if (towerBeingPlaced != null) {
            towerBeingPlaced.draw(g);
        }
    }
}
