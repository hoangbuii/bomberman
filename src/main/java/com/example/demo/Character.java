package com.example.demo;

public abstract class Character extends DynamicEntity {
    protected double speed;

    public Character() {
        super();
    }

    public Character(int x, int y) {
        super(x, y);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public abstract void dead();

}
