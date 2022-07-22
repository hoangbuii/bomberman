package com.example.demo;

public abstract class Enemy extends Character {
    protected int score;

    public Enemy() {
        super();
    }

    public Enemy(int x, int y) {
        super(x, y);
    }

    public int getScore() {
        return score;
    }

    public abstract void dead();

    public abstract void stopAnimation();

    public abstract void startAnimation();

}
