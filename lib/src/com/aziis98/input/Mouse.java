package com.aziis98.input;

// Copyright 2016 Antonio De Lucreziis

import com.aziis98.event.*;

import java.awt.*;
import java.awt.event.*;

public class Mouse {

    private static Point position     = new Point();
    private static Point prevPosition = new Point();

    private static int button = 0;
    private static int scroll = 0;

    public static Point getPosition() {
        return position;
    }

    public static Point getDelta() {
        return new Point( getDeltaX(), getDeltaY() );
    }

    public static int getX() {
        return (int) position.getX();
    }

    public static int getY() {
        return (int) position.getY();
    }

    public static int getDeltaX() {
        return (int) (position.getX() - prevPosition.getX());
    }

    public static int getDeltaY() {
        return (int) (position.getY() - prevPosition.getY());
    }

    public static int getScroll() {
        return scroll;
    }

    public static int getButton() {
        return button;
    }

    public static EventMouse getCurrentState() {
        return new EventMouse( getX(), getY(), getDeltaX(), getDeltaY(), getButton(), getScroll() );
    }

    public static void addListeners(Component component) {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button = e.getButton();
                EventManager.invoke( new EventMouseClicked( getCurrentState() ) );
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button = e.getButton();
                EventManager.invoke( new EventMousePressed( getCurrentState() ) );
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button = 0;

                EventManager.invoke( new EventMouseReleased( getCurrentState() ) );
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                button = e.getButton();
                prevPosition.setLocation( position );
                position = e.getPoint();

                EventManager.invoke( new EventMouseMoved( getCurrentState() ) );
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                prevPosition.setLocation( position );
                position = e.getPoint();

                EventManager.invoke( new EventMouseDragged( getCurrentState() ) );
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                scroll = e.getWheelRotation();

                EventManager.invoke( new EventMouseScrolled( getCurrentState() ) );
            }
        };

        component.addMouseListener( mouseAdapter );
        component.addMouseMotionListener( mouseAdapter );
        component.addMouseWheelListener( mouseAdapter );

    }

    public static void reset() {
        scroll = 0;
    }




    public static class EventMouse implements IEvent {

        private final int x, y, dx, dy, button, scroll;

        public EventMouse(int x, int y, int dx, int dy, int button, int scroll) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.button = button;
            this.scroll = scroll;
        }

        public EventMouse(EventMouse eventMouse) {
            this( eventMouse.getX(), eventMouse.getY(), eventMouse.getDeltaX(), eventMouse.getDeltaY(), eventMouse.getButton(), eventMouse.getScroll() );
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getDeltaX() {
            return dx;
        }

        public int getDeltaY() {
            return dy;
        }

        public int getButton() {
            return button;
        }

        public int getScroll() {
            return scroll;
        }
    }


    public static class EventMouseDragged extends EventMouse {
        public EventMouseDragged(EventMouse eventMouse) {
            super( eventMouse );
        }
    }

    public static class EventMouseMoved extends EventMouse {
        public EventMouseMoved(EventMouse eventMouse) {
            super( eventMouse );
        }
    }

    public static class EventMousePressed extends EventMouse {
        public EventMousePressed(EventMouse eventMouse) {
            super( eventMouse );
        }
    }

    public static class EventMouseClicked extends EventMouse {
        public EventMouseClicked(EventMouse eventMouse) {
            super( eventMouse );
        }
    }

    public static class EventMouseReleased extends EventMouse {
        public EventMouseReleased(EventMouse eventMouse) {
            super( eventMouse );
        }
    }

    public static class EventMouseScrolled extends EventMouse {
        public EventMouseScrolled(EventMouse eventMouse) {
            super( eventMouse );
        }
    }






}
