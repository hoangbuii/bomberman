package com.example.demo;

import javax.sound.sampled.*;
import java.io.*;

public class Sound {
    private String name;

    /**
     * Constructor.
     */

    public Sound() {
    }

    public Sound(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Play.
     */

    public void Play(String name) {
        try {
            String path =  "C:\\Users\\Hoang Bui\\IdeaProjects\\oneal_dead2\\src\\main\\resources\\com\\example\\Sound\\" + name + ".wav";
            File file = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loop.
     */

    public void Loop(String name) {
        try {
            String path = "E:\\demo\\demo\\src\\main\\resources\\com\\example\\Sound\\" + name + ".wav";
            File file = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

