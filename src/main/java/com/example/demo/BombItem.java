package com.example.demo;

public class BombItem extends StaticEntity {
    public BombItem() {
        super("powerup_bombs");
    }

    public BombItem(int x, int y) {
        super(x, y, "powerup_bombs");
    }

}
