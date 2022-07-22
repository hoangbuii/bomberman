package com.example.demo;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public static Sound sound = new Sound();
    // Constant Atributes
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int SCALE = 40;
    public static final int SCREEN_WIDTH = SCALE * WIDTH;
    public static final int SCREEN_HEIGHT = SCALE * HEIGHT;

    // Pane

    public static AnchorPane gRenderer = new AnchorPane();

    // list of entities

    public static List<Wall> walls = new ArrayList<>();
    public static List<Grass> grasses = new ArrayList<>();
    public static Bomber player = new Bomber();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Brick> bricks = new ArrayList<>();
    public static List<Bomb> bombList = new ArrayList<>();
    public static ArrayList<Flame> flames = new ArrayList<>();
    public static Portal portal = new Portal();
    public static List<SpeedItem> speedItems = new ArrayList<>();
    public static List<FlameItem> flameItems = new ArrayList<>();
    public static List<BombItem> bombItems = new ArrayList<>();
    public static Brick portalBrick = new Brick();

    public static int time = 200;
    public static Label timeLabel = new Label();

    public static int score;
    public static Label scoreLabel = new Label();

    public static int lives = 2;
    public static Label livesLabel = new Label();

    public static Stage stage;

    public static Scene mainMenu;

    public static Scene gameplayScene;

    public static int level = 2;

    public static final int maxLevel = 2;

    public void readMap(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scan = new Scanner(file);
            for (int i = 0; i < HEIGHT; i++) {
                String s = scan.nextLine();
                for (int j = 0; j < WIDTH; j++) {
                    switch (s.charAt(j)) {
                        case '#':
                            walls.add(new Wall(j * SCALE, i * SCALE));
                            break;
                        default:
                            grasses.add(new Grass(j * SCALE, i * SCALE));
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void preRender() {
        for (Wall wall : walls) {
            wall.render();
        }

        for (Grass grass : grasses) {
            grass.render();
        }
    }

    public static void render() {
        ArrayList<Integer> existedEntityIndexes = new ArrayList<>();

        int k = (int) (Math.random() * grasses.size());
        while (((grasses.get(k).getX() == SCALE || grasses.get(k).getX() == 2 * SCALE)
                && grasses.get(k).getY() == SCALE) || (grasses.get(k).getX() == SCALE
                && grasses.get(k).getY() == 2 * SCALE)) {
            k = (int) (Math.random() * grasses.size());
        };
        portal.setFrame(new Rectangle(0, 0, 0, 0));
        portal.setX(grasses.get(k).getX());
        portal.setY(grasses.get(k).getY());
        portal.render();
        portalBrick.setFrame(new Rectangle(0, 0, 0, 0));
        portalBrick.setX(grasses.get(k).getX());
        portalBrick.setY(grasses.get(k).getY());
        bricks.add(portalBrick);
        portalBrick.render();
        existedEntityIndexes.add(k);

        for (int i = 0; i < 3; i++) {
            int j = (int) (Math.random() * grasses.size());
            while (((grasses.get(j).getX() == SCALE || grasses.get(j).getX() == 2 * SCALE)
                    && grasses.get(j).getY() == SCALE) || (grasses.get(j).getX() == SCALE
                    && grasses.get(j).getY() == 2 * SCALE) || existedEntityIndexes.contains(j)) {
                j = (int) (Math.random() * grasses.size());
            };
            switch ((int) (Math.random() * 3)) {
                case 0:
                    SpeedItem speedItem = new SpeedItem(grasses.get(j).getX(), grasses.get(j).getY());
                    Main.speedItems.add(speedItem);
                    speedItem.render();
                    break;
                case 1:
                    FlameItem flameItem = new FlameItem(grasses.get(j).getX(), grasses.get(j).getY());
                    Main.flameItems.add(flameItem);
                    flameItem.render();
                    break;
                case 2:
                    BombItem bombItem = new BombItem(grasses.get(j).getX(), grasses.get(j).getY());
                    Main.bombItems.add(bombItem);
                    bombItem.render();
                    break;
            }
            Brick itemBrick = new Brick(grasses.get(j).getX(), grasses.get(j).getY());
            bricks.add(itemBrick);
            itemBrick.render();
            existedEntityIndexes.add(j);
        }

        for (int i = 0; i < 46; i ++) {
            int j = (int) (Math.random() * grasses.size());
            while (((grasses.get(j).getX() == SCALE || grasses.get(j).getX() == 2 * SCALE)
                    && grasses.get(j).getY() == SCALE) || (grasses.get(j).getX() == SCALE
                    && grasses.get(j).getY() == 2 * SCALE) || existedEntityIndexes.contains(j)) {
                j = (int) (Math.random() * grasses.size());
            };
            Brick newBrick = new Brick(grasses.get(j).getX(), grasses.get(j).getY());
            bricks.add(newBrick);
            newBrick.render();
            existedEntityIndexes.add(j);
        }
        if (player.getFrame() == null) {
            player.setFrame(new Rectangle(0, 0, 0, 0));
            if (player.isDead()) {
                player.setDead(false);
                player.setStep(0);
                player.loadImage("player.png");
            }
        }
        player.setX(SCALE);
        player.setY(SCALE);
        player.render();

        switch (level) {
            case 1:
                for (int i = 0; i < 6; i++) {
                    int j = (int) (Math.random() * grasses.size());
                    while (((grasses.get(j).getX() == SCALE || grasses.get(j).getX() == 2 * SCALE)
                            && grasses.get(j).getY() == SCALE) || (grasses.get(j).getX() == SCALE
                            && grasses.get(j).getY() == 2 * SCALE) || existedEntityIndexes.contains(j)) {
                        j = (int) (Math.random() * grasses.size());
                    }
                    ;
                    Balloom newBalloom = new Balloom(grasses.get(j).getX(), grasses.get(j).getY());
                    newBalloom.render();
                    enemies.add(newBalloom);
                    existedEntityIndexes.add(j);
                }
                break;
            case 2:
                for (int i = 0; i < 3; i++) {
                    int j = (int) (Math.random() * grasses.size());
                    while (((grasses.get(j).getX() == SCALE || grasses.get(j).getX() == 2 * SCALE)
                            && grasses.get(j).getY() == SCALE) || (grasses.get(j).getX() == SCALE
                            && grasses.get(j).getY() == 2 * SCALE) || existedEntityIndexes.contains(j)) {
                        j = (int) (Math.random() * grasses.size());
                    }
                    ;
                    Balloom newBalloom = new Balloom(grasses.get(j).getX(), grasses.get(j).getY());
                    newBalloom.render();
                    enemies.add(newBalloom);
                    existedEntityIndexes.add(j);
                }
                for (int i = 0; i < 3; i++) {
                    int j = (int) (Math.random() * grasses.size());
                    while (((grasses.get(j).getX() == SCALE || grasses.get(j).getX() == 2 * SCALE)
                            && grasses.get(j).getY() == SCALE) || (grasses.get(j).getX() == SCALE
                            && grasses.get(j).getY() == 2 * SCALE) || existedEntityIndexes.contains(j)) {
                        j = (int) (Math.random() * grasses.size());
                    }
                    ;
                    Oneal oneal = new Oneal(grasses.get(j).getX(), grasses.get(j).getY());
                    oneal.render();
                    enemies.add(oneal);
                    existedEntityIndexes.add(j);
                }
        }
    }

    public static void removeRender() {
        player.remove();
        portal.remove();
        while (!speedItems.isEmpty()) {
            speedItems.get(speedItems.size() - 1).remove();
            speedItems.remove(speedItems.size() - 1);
        }
        while (!flameItems.isEmpty()) {
            flameItems.get(flameItems.size() - 1).remove();
            flameItems.remove(flameItems.size() - 1);
        }
        while (!bombItems.isEmpty()) {
            bombItems.get(bombItems.size() - 1).remove();
            bombItems.remove(bombItems.size() - 1);
        }
        while (!bricks.isEmpty()) {
            bricks.get(bricks.size() - 1).remove();
            bricks.remove(bricks.size() - 1);
        }
        while (!enemies.isEmpty()) {
            enemies.get(enemies.size() - 1).stopAnimation();
            enemies.get(enemies.size() - 1).remove();
            enemies.remove(enemies.size() - 1);
        }
    }

//    public static boolean containsBrick(int x, int y) {
//        for (Brick brick : bricks) {
//            if (brick.getX() == x && brick.getY() == y) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        time--;
        if (time >= 0) {
            timeLabel.setText("Time: " + time);
        }
    }));

    public static AnimationTimer gamePlay = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (time == 0) {
                player.setDead(true);
//                timeline.stop();
                player.dead();
            }

            for (Flame flame : flames) {
                for (int i = 0; i < enemies.size(); i++) {
                    if (Collision.isCollision(flame, enemies.get(i))) {
                        enemies.get(i).dead();
                        score += enemies.get(i).getScore();
                        scoreLabel.setText("score: " + score);
                        enemies.remove(i);
                        i--;
                    }
                }
                for (int i = 0; i < bricks.size(); i++) {
                    if (Collision.isCollision(flame, bricks.get(i))) {
                        bricks.get(i).destroy();
                        bricks.remove(i);
                        i--;
                    }
                }
                if (Collision.isCollision(flame, player)) {
                    player.setDead(true);
                    player.dead();
                }
                if (Collision.isCollision(flame, portalBrick)) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
//                if (!containsBrick(portal.getX(), portal.getY())) {
                if (!portalBrick.isRendered()) {
                    if (Collision.isCollision(flame, portal)) {
                        for (int i = 0; i < 6; i++) {
                            Balloom newBalloom = new Balloom(portal.getX(), portal.getY());
                            newBalloom.render();
                            enemies.add(newBalloom);
                        }
                    }
                }
                for (int i = 0; i < speedItems.size(); i++) {
                    if (Collision.isCollision(flame, speedItems.get(i))) {
                        speedItems.get(i).remove();
                        speedItems.remove(i);
                        i--;
                    }
                }
            }

            for (Bomb bomb : bombList) {
                if (Collision.isCollision(player, bomb) && bomb.isWalkAble()) {
                    bomb.disableWalkability();
                }
            }

            if (player.isRendered() && !player.isDead()) {
                for (Character enemy : enemies) {
                    if (Collision.isCollision(enemy, player)) {
                        player.setDead(true);
                        player.dead();
                    }
                }
            }

            if (enemies.isEmpty()) {
                if (Collision.isCollision(player, portal)) {
                    if (level <= maxLevel) {
                        level++;
                        gamePlay.stop();
                        removeRender();
                        render();
                        gamePlay.start();
                        time = 200;
                        lives = 2;
                        livesLabel.setText("Lives: " + Main.lives);
                    } else {
                        try {
                            Parent gameEnding = FXMLLoader.load(this.getClass()
                                    .getResource("gameEnding.fxml"));
                            Scene gameEndingScene = new Scene(gameEnding);
                            Main.changScene(gameEndingScene);
                            gameEnding.requestFocus();
                            gameEnding.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent keyEvent) {
                                    changScene(mainMenu);
                                }
                            });
                        } catch(Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }

            for (int i = 0; i < speedItems.size(); i++) {
                if (player.isRendered()) {
                    if (Collision.isCollision(player, speedItems.get(i))) {
                        if (MainMenu.soundChecked) {
                            sound.Play("14_Get Items");
                        }
                        player.increaseSpeed();
                        player.setX(speedItems.get(i).getX());
                        player.setY(speedItems.get(i).getY());
                        player.update();
                        speedItems.get(i).remove();
                        speedItems.remove(i);
                        i --;
                    }
                }
            }

            for (int i = 0; i < flameItems.size(); i++) {
                if (player.isRendered()) {
                    if (Collision.isCollision(player, flameItems.get(i))) {
                        if (MainMenu.soundChecked) {
                            sound.Play("14_Get Items");
                        }
                        Bomb.increaseRadius();
                        flameItems.get(i).remove();
                        flameItems.remove(i);
                        i--;
                    }
                }
            }

            for (int i = 0; i < bombItems.size(); i++) {
                if (player.isRendered()) {
                    if (Collision.isCollision(player, bombItems.get(i))) {
                        if (MainMenu.soundChecked) {
                            sound.Play("14_Get Items");
                        }
                        Bomb.increaseMaxBombs();
                        bombItems.get(i).remove();
                        bombItems.remove(i);
                        i--;
                    }
                }
            }

        }

    };

    public static void changScene(Scene scene) {
        stage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Bomberman");
        gameplayScene = new Scene(gRenderer, SCREEN_WIDTH, SCREEN_HEIGHT + SCALE);
        readMap("PreRenderedMap.txt");
        preRender();
        render();
        try {
            Parent menu = FXMLLoader.load(this.getClass().getResource("mainMenu.fxml"));
            primaryStage.setScene(new Scene(menu));
            primaryStage.show();
            menu.requestFocus();
            menu.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
//                    try {
//                        Parent levelScene = FXMLLoader.load(this.getClass().getResource(
//                                "level.fxml"));
//                        primaryStage.setScene(new Scene(levelScene));
//                        LevelLabel levelLabel = new LevelLabel();
//                        levelLabel.setLevelLabel("Level " + level);
//                        Thread.sleep(2000);
//                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    }

                    primaryStage.setScene(gameplayScene);

//                        primaryStage.setX(mainMenu.getX() + mainMenu.getWidth() / 2);

                    timeline.setCycleCount(Animation.INDEFINITE);
                        timeline.play();



                    timeLabel.setText("Time: " + time);
                    timeLabel.setLayoutX(SCALE);
                    timeLabel.setLayoutX(SCALE);
                    timeLabel.setLayoutY(SCREEN_HEIGHT + SCALE / 8);
                    timeLabel.setScaleX(SCALE / 20);
                    timeLabel.setScaleY(SCALE / 20);
                    gRenderer.getChildren().add(timeLabel);

                    scoreLabel.setText("Score: " + score);
                    scoreLabel.setLayoutX(SCALE * 5);
                    scoreLabel.setLayoutY(SCREEN_HEIGHT + SCALE / 8);
                    scoreLabel.setScaleX(SCALE / 20);
                    scoreLabel.setScaleY(SCALE / 20);
                    gRenderer.getChildren().add(scoreLabel);

                    livesLabel.setText("Lives: " + lives);
                    livesLabel.setLayoutX(SCALE * 10);
                    livesLabel.setLayoutY(SCREEN_HEIGHT + SCALE / 8);
                    livesLabel.setScaleX(SCALE / 20);
                    livesLabel.setScaleY(SCALE / 20);
                    gRenderer.getChildren().add(livesLabel);

                    gamePlay.start();
                    if (MainMenu.musicChecked) {
                        sound.Play("03_Stage Theme");
                    }
                    gRenderer.requestFocus();

                    final boolean[] isPLaying = {true};

                    gRenderer.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if (keyEvent.isControlDown()) {
                                if (keyEvent.getCode() == KeyCode.R) {
                                    gamePlay.stop();
                                    removeRender();
                                    render();
                                    gamePlay.start();
                                    time = 200;
                                    lives = 2;
                                    livesLabel.setText("Lives: " + Main.lives);
                                    if (timeline.getStatus() != Animation.Status.RUNNING) {
                                            timeline.play();

                                    }
                                    if (!isPLaying[0]) {
                                        isPLaying[0] = true;
                                    }
                                }
                            }

                            if (keyEvent.getCode() == KeyCode.ENTER) {
                                if (isPLaying[0]) {
                                    gamePlay.stop();
                                    for (Enemy enemy : enemies) {
                                        enemy.stopAnimation();
                                    }
                                    timeline.pause();
                                    isPLaying[0] = false;
                                } else {
                                    gamePlay.start();
                                    for(Enemy enemy : enemies) {
                                        enemy.startAnimation();
                                    }
                                        timeline.play();

                                    isPLaying[0] = true;
                                }
                            }

                            if (player.isRendered() && !player.isDead() && isPLaying[0]) {
                                player.handleEvent(keyEvent);
                            }

                        }
                    });

                    gRenderer.setOnKeyReleased(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if (!player.isDead() && isPLaying[0]) {
                                if (keyEvent.getCode() == KeyCode.UP) {
                                    player.loadImage("player_up_0.png");
                                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                                    player.loadImage("player_down_0.png");
                                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                                    player.loadImage("player_left_0.png");
                                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                                    player.loadImage("player_right_0.png");
                                }
                                player.update();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}