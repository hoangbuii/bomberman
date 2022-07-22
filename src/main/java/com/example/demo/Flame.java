package com.example.demo;

import javafx.animation.AnimationTimer;

public class Flame extends DynamicObject {
    private String direction;
    public Sound sound = new Sound();
    public Flame(String direction, int x, int y) {
        super(x, y);
        this.direction = direction;
    }

    public void stepBurn() {
        loadImage("explosion_" + direction + ((int) step / 15) + ".png");
        step++;
        update();
    }

    AnimationTimer burn = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (step < 45) {
                stepBurn();
            }
            if (step == 45) {
                burn.stop();
                remove();
            }
        }
    };

    public void start() {
        step = 0;
        burn.start();
        sound.Play("15_Explosion Bomb");
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
