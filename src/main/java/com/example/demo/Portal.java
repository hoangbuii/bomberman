package com.example.demo;

public class Portal extends StaticEntity {
    private boolean isEnabled = false;

    public Portal() {
        super("portal");
    }

    public Portal(int x, int y) {
        super(x, y, "portal");
    }

    public void enable() {
        isEnabled = true;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}