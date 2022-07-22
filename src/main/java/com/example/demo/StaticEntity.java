package com.example.demo;

public class StaticEntity extends Entity {
    public StaticEntity(String object) {
        super();
        loadImage(object + ".png");
    }

    public StaticEntity(int x, int y, String object) {
        super();
        setX(x);
        setY(y);
        loadImage(object + ".png");
    }
}
