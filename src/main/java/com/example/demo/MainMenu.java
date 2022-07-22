package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class MainMenu {

    @FXML
    public ImageView musicCheck;

    @FXML
    public ImageView soundCheck;

    public static boolean musicChecked = true;
    public static boolean soundChecked = true;

    public void music() {
        musicChecked = !musicChecked;
        musicCheck.setImage(new Image(Objects.requireNonNull(
                this.getClass().getResource(musicChecked ? "bomb_0.png" : "none.png")).toExternalForm(),
                Main.SCALE, Main.SCALE, true, true));
    }

    public void sound() {
        soundChecked = !soundChecked;
        soundCheck.setImage(new Image(Objects.requireNonNull(
                this.getClass().getResource(soundChecked ? "bomb_0.png" : "none.png")).toExternalForm(),
                Main.SCALE, Main.SCALE, true, true));
    }
}