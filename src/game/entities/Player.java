package game.entities;

import game.enums.Difficulty;

public class Player {
    private int lives;
    private int money;
    private int kills;
    private int killTarget;
    private Difficulty difficulty;

    public Player(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.kills = 0;
        setupDifficulty();
    }

    private void setupDifficulty() {
        switch (difficulty) {
            case EASY -> {
                lives = 20;
                money = 400;
                killTarget = 250;
            }
            case HARD -> {
                lives = 5;
                money = 100;
                killTarget = 1000;
            }
            case NORMAL -> {
                lives = 10;
                money = 200;
                killTarget = 500;
            }
        }
    }

    public void loseLife() {
        lives--;
    }

    public void earnMoney(int amount) {
        money += amount;
    }

    public void spendMoney(int amount) {
        money -= amount;
    }

    public void addKill() {
        kills++;
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public boolean hasWon() {
        return kills >= killTarget;
    }

    public int getLives() { return lives; }
    public int getMoney() { return money; }
    public int getKills() { return kills; }
    public int getKillTarget() { return killTarget; }
    public Difficulty getDifficulty() { return difficulty; }
}
