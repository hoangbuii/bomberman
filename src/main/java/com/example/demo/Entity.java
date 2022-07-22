package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public abstract class Entity {
    protected Image texture;
    protected ImageView imageView = new ImageView();
    protected Rectangle frame = new Rectangle(0, 0, 0, 0);

    public Entity() {
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Rectangle getFrame() {
        return frame;
    }

    public void setFrame(Rectangle frame) {
        this.frame = frame;
    }

    public int getX() {
        return (int) frame.getX();
    }

    public void setX(int x) {
        frame.setX(x);
    }

    public int getY() {
        return (int) frame.getY();
    }

    public void setY(int y) {
        frame.setY(y);
    }

    public int getHeight() {
        return (int) frame.getHeight();
    }

    public void setHeight(int height) {
        frame.setHeight(height);
    }

    public int getWidth() {
        return (int) frame.getWidth();
    }

    public void setWidth(int width) {
        frame.setWidth(width);
    }

    public boolean isRendered() {
        return texture != null && frame != null && Main.gRenderer.getChildren()
                .contains(imageView);
    }

    public void loadImage(String filePath) {
        try {
            texture = new Image(Objects.requireNonNull(
                    this.getClass().getResource(filePath)).toExternalForm(),
                    Main.SCALE, Main.SCALE, true, true);
            frame.setWidth(texture.getWidth());
            frame.setHeight(texture.getHeight());
        } catch (Exception e) {
            System.out.println("Fatal Error!!!");
            System.out.println("Can't load: " + filePath);
        }
    }

    public void render() {
        update();
        Main.gRenderer.getChildren().add(imageView);
    }

    public void update() {
        imageView.setX(frame.getX());
        imageView.setY(frame.getY());
        imageView.setImage(texture);
    }

    public void removeTextureInImageView() {
        imageView.setImage(null);
    }

    public void remove() {
        Main.gRenderer.getChildren().remove(imageView);
        frame = null;
    }

}