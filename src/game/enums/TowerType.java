package game.enums;

import game.Coordinate;
import game.towers.BlackHole;
import game.towers.Missil;
import game.towers.Sun;
import game.towers.Tower;

import java.util.function.Function;

public enum TowerType {
    MISSIL("Missil", 60, 250, Missil::new),
    BLACKHOLE("Blackhole", 100, 400, BlackHole::new),
    SUN("Sun", 300, 550, Sun::new);

    public final String displayName;
    public final int cost;
    public final int iconY;
    public final Function<Coordinate, Tower> factory;

    TowerType(String displayName, int cost, int iconY, Function<Coordinate, Tower> factory) {
        this.displayName = displayName;
        this.cost = cost;
        this.iconY = iconY;
        this.factory = factory;
    }
}
