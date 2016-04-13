package com.aziis98.test;

// Copyright 2016 Antonio De Lucreziis

import com.aziis98.canvas.*;
import com.aziis98.event.*;
import com.aziis98.input.Mouse.*;

import java.awt.*;

public class Test1 {

    public static void main(String[] args) {
        CanvasApplication.launch( new TestApp() );
    }

    public static class TestApp extends CanvasApp {

        @Override
        public void init() {
            setWindowTitle( "Test1" );
            setWindowSize( 800, 600 );

            EventManager.register( this );
        }

        @Override
        public void update() {

        }

        @Override
        public void render(Graphics2D g) {

        }


        public void mouse1(EventMouseDragged e) {
            System.out.println(e.getButton());
        }

    }


}
