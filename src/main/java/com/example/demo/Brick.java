package com.example.demo;

import javafx.animation.AnimationTimer;

public class Brick extends DynamicObject {
    public Brick() {
        super();
        loadImage("brick.png");
    }

    public Brick(int x, int y) {
        super(x, y);
        loadImage("brick.png");
    }

    private void stepDestroy() {
        loadImage("brick_exploded" + ((int) step / 25) + ".png");
        step++;
        update();
    }

    AnimationTimer destroy = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (step < 75) {
                stepDestroy();
            }
            if (step == 75) {
                int x = getX();
                int y = getY();
                remove();
                Main.bricks.remove(this);
                destroy.stop();
//                if ((int) (Math.random() * 2) == 1) {
//                    switch ((int) (Math.random() * 3)) {
//                        case 0:
//                            SpeedItem speedItem = new SpeedItem(x, y);
//                            Main.speedItems.add(speedItem);
//                            speedItem.render();
//                            break;
//                        case 1:
//                            FlameItem flameItem = new FlameItem(x, y);
//                            Main.flameItems.add(flameItem);
//                            flameItem.render();
//                            break;
//                        case 2:
//                            BombItem bombItem = new BombItem(x, y);
//                            Main.bombItems.add(bombItem);
//                            bombItem.render();
//                            break;
//                    }
//                }
            }
        }
    };

    public void destroy() {
        step = 0;
        destroy.start();
    }
}
