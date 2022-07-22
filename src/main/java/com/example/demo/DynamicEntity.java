package com.example.demo;

public class DynamicEntity extends Entity {
    protected int step = 0;

    public DynamicEntity() {
        super();
    }

    public DynamicEntity(int x, int y) {
        super();
        setX(x);
        setY(y);
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
