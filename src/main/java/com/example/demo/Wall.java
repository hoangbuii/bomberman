package com.example.demo;

public class Wall extends StaticEntity {
    public Wall() {
        super("wall");
    }

    public Wall(int x, int y) {
        super(x, y, "wall");
    }

}