package com.aziis98.canvas;

// Copyright 2016 Antonio De Lucreziis

import javax.swing.*;
import java.awt.*;

public abstract class CanvasApp implements IControl {

    private Canvas javaCanvas;
    private JFrame javaFrame;

    /**
     * Set the window title and the size here
     */
    @Override
    public abstract void init();

    @Override
    public abstract void update();

    @Override
    public abstract void render(Graphics2D g);

    public void setup(JFrame nativeFrame, Canvas nativeCanvas) {
        this.javaFrame = nativeFrame;
        this.javaCanvas = nativeCanvas;

        this.init();
    }

    public FontMetrics getFontMetrics(Font font) {
        return javaCanvas.getFontMetrics( font );
    }

    public int getWindowWidth() {
        return javaCanvas.getWidth();
    }

    public int getWindowHeight() {
        return javaCanvas.getHeight();
    }

    public void setWindowSize(int width, int height) {
        this.javaFrame.setSize( width, height );
    }

    public void setWindowTitle(String title) {
        this.javaFrame.setTitle( title );
    }

}
