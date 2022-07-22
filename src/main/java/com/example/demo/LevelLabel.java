package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LevelLabel {

    @FXML
    private Label levelLabel;

    public void setLevelLabel(String level) {
        levelLabel.setText(level);
    }

}
