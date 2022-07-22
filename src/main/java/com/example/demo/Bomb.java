package com.example.demo;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class Bomb extends DynamicObject {
    private static int radius = 1;
    private static int maxBombs = 1;
    private boolean walkAble = true;
    private ArrayList<Flame> currentFlames = new ArrayList<>();

    public Bomb() {
        super();
        loadImage("bomb_0.png");
        step = 0;
    }

    public boolean isWalkAble() {
        return walkAble;
    }

    public void disableWalkability() {
        walkAble = false;
    }

    public static int getMaxBombs() {
        return maxBombs;
    }

    public static void increaseRadius() {
        radius++;
    }

    public static void increaseMaxBombs() {
        maxBombs++;
    }

    public void stepBomb() {
        loadImage("bomb_" + ((int) step / 50) + ".png");
        step++;
        update();
    }

    AnimationTimer timerBomb = new AnimationTimer() {
        @Override
            public void handle(long l) {
            if (step < 150) {
                stepBomb();
            }
            if (step == 150) {
                trigger();
            }
        }
    };

    public void trigger() {
        step = 0;
        for (Flame flame : currentFlames) {
            Main.flames.add(flame);
            flame.render();
            flame.start();
        }
        timer.start();
        timerBomb.stop();
    }

    public void stepExplode() {
        loadImage("bomb_exploded" + ((int) step / 15) + ".png");
        step++;
        update();
    }

    private void removeBomb() {
        Main.bombList.remove(this);
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (step < 45) {
                stepExplode();
            }
            if (step == 45) {
                removeBomb();
                for (Flame flame : currentFlames) {
                    Main.flames.remove(flame);
                }
                remove();
                timer.stop();
            }
        }
    };

    public void explode() {
        step = 0;
        timerBomb.start();

        currentFlames.add(new Flame("mid", getX(), getY()));

        boolean skip = false;
        for (int i = 1; i < radius; i++) {
            for (Wall wall : Main.walls) {
                if (wall.getX() == this.getX() && wall.getY() + Main.SCALE * i == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
            Flame newFlame = new Flame("vertical", getX(), getY() - Main.SCALE * i);
            currentFlames.add(newFlame);
            for (Brick brick : Main.bricks) {
                if (brick.getX() == this.getX() && brick.getY() + Main.SCALE * i == this.getY()) {
                    newFlame.setDirection("");
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
        }
        if (!skip) {
            for (Wall wall : Main.walls) {
                if (wall.getX() == this.getX() && wall.getY() + Main.SCALE * radius
                        == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                Flame newFlame = new Flame("vertical_top_last", getX(), getY() - Main.SCALE
                        * radius);
                currentFlames.add(newFlame);
                for (Brick brick: Main.bricks) {
                    if (brick.getX() == this.getX() && brick.getY() + Main.SCALE * radius == this.getY()) {
                        newFlame.setDirection("");
                        break;
                    }
                }
            }
        }





        skip = false;
        for (int i = 1; i < radius; i++) {
            for (Wall wall : Main.walls) {
                if (wall.getX() == this.getX() && wall.getY() - Main.SCALE * i == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
            Flame newFlame = new Flame("vertical", getX(), getY() + Main.SCALE * i);
            currentFlames.add(newFlame);
            for (Brick brick : Main.bricks) {
                if (brick.getX() == this.getX() && brick.getY() - Main.SCALE * i == this.getY()) {
                    newFlame.setDirection("");
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
        }

        if (!skip) {
            for (Wall wall : Main.walls) {
                if (wall.getX() == this.getX() && wall.getY() - Main.SCALE * radius
                        == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                Flame newFlame = new Flame("vertical_down_last", getX(),getY() + Main.SCALE
                        * radius);
                currentFlames.add(newFlame);
                for (Brick brick: Main.bricks) {
                    if (brick.getX() == this.getX() && brick.getY() - Main.SCALE * radius == this.getY()) {
                        newFlame.setDirection("");
                        break;
                    }
                }
            }
        }





        skip = false;
        for (int i = 1; i < radius; i++) {
            for (Wall wall : Main.walls) {
                if (wall.getX() + Main.SCALE * i == this.getX() && wall.getY() == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
            Flame newFlame = new Flame("horizontal", getX() - Main.SCALE * i, getY());
            currentFlames.add(newFlame);
            for (Brick brick : Main.bricks) {
                if (brick.getX() + Main.SCALE * i == this.getX() && brick.getY() == this.getY()) {
                    newFlame.setDirection("");
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
        }
        if (!skip) {
            for (Wall wall : Main.walls) {
                if (wall.getX() + Main.SCALE * radius == this.getX() && wall.getY() == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                Flame newFlame = new Flame("horizontal_left_last", getX() - Main.SCALE * radius
                        , getY());
                currentFlames.add(newFlame);
                for (Brick brick: Main.bricks) {
                    if (brick.getX() + Main.SCALE * radius == this.getX() && brick.getY() == this.getY()) {
                        newFlame.setDirection("");
                        break;
                    }
                }
            }
        }





        skip = false;
        for (int i = 1; i < radius; i++) {
            for (Wall wall : Main.walls) {
                if (wall.getX() - Main.SCALE * i == this.getX() && wall.getY() == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
            Flame newFlame = new Flame("horizontal", getX() + Main.SCALE * i, getY());
            currentFlames.add(newFlame);
            for (Brick brick : Main.bricks) {
                if (brick.getX() - Main.SCALE * i == this.getX() && brick.getY() == this.getY()) {
                    newFlame.setDirection("");
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
        }
        if (!skip) {
            for (Wall wall : Main.walls) {
                if (wall.getX() - Main.SCALE * radius == this.getX() && wall.getY() == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                Flame newFlame = new Flame("horizontal_right_last", getX() + Main.SCALE * radius
                        , getY());
                currentFlames.add(newFlame);
                for (Brick brick: Main.bricks) {
                    if (brick.getX() - Main.SCALE * radius == this.getX() && brick.getY() == this.getY()) {
                        newFlame.setDirection("");
                        break;
                    }
                }
            }
        }

    }

}
