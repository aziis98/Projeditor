package com.aziis98.input;

// Copyright 2016 Antonio De Lucreziis

import com.aziis98.event.*;

import java.awt.*;
import java.awt.event.*;

public class Keyboard {

    private static boolean[] states = new boolean[1024];
    private static char lastChar = 0;

    public static boolean keyState(int keyCode) {
        return states[keyCode];
    }

    public static char getLastChar() {
        return lastChar;
    }

    private static EventKeyboard getCurrentState() {
        return new EventKeyboard( lastChar, 0, false, false, false );
    }

    public static void addListeners(Component component) {
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                lastChar = e.getKeyChar();

                EventKeyboardTyped keyboardPress = new EventKeyboardTyped( getCurrentState() );
                {
                    keyboardPress.typedChar = e.getKeyChar();
                    keyboardPress.keyCode = e.getKeyCode();

                    keyboardPress.modShift = e.isShiftDown();
                    keyboardPress.modAlt = e.isAltDown();
                    keyboardPress.modCtrl = e.isControlDown();
                }
                EventManager.invoke( keyboardPress );
            }

            @Override
            public void keyPressed(KeyEvent e) {
                states[e.getKeyCode()] = true;

                EventKeyboardPressed keyboardPress = new EventKeyboardPressed( getCurrentState() );
                {
                    keyboardPress.typedChar = e.getKeyChar();
                    keyboardPress.keyCode = e.getKeyCode();

                    keyboardPress.modShift = e.isShiftDown();
                    keyboardPress.modAlt = e.isAltDown();
                    keyboardPress.modCtrl = e.isControlDown();
                }
                EventManager.invoke( keyboardPress );
            }

            @Override
            public void keyReleased(KeyEvent e) {
                states[e.getKeyCode()] = false;

                EventKeyboardReleased keyboardPress = new EventKeyboardReleased( getCurrentState() );
                {
                    keyboardPress.typedChar = e.getKeyChar();
                    keyboardPress.keyCode = e.getKeyCode();

                    keyboardPress.modShift = e.isShiftDown();
                    keyboardPress.modAlt = e.isAltDown();
                    keyboardPress.modCtrl = e.isControlDown();
                }
                EventManager.invoke( keyboardPress );
            }
        };


        component.addKeyListener( keyAdapter );
    }

    public static void reset() {
        lastChar = 0;
    }

    public static class EventKeyboard implements IEvent {
        char typedChar;
        int keyCode;
        boolean modShift, modAlt, modCtrl;

        public EventKeyboard(char typedChar, int keyCode, boolean modShift, boolean modAlt, boolean modCtrl) {
            this.typedChar = typedChar;
            this.keyCode = keyCode;
            this.modShift = modShift;
            this.modAlt = modAlt;
            this.modCtrl = modCtrl;
        }

        public EventKeyboard(EventKeyboard eventKeyboard) {
            this( eventKeyboard.typedChar, eventKeyboard.keyCode, eventKeyboard.modShift, eventKeyboard.modAlt, eventKeyboard.modCtrl );
        }

        public char getTypedChar() {
            return typedChar;
        }

        public int getKeyCode() {
            return keyCode;
        }

        public boolean isShiftPressed() {
            return modShift;
        }

        public boolean isAltPressed() {
            return modAlt;
        }

        public boolean isCtrlPressed() {
            return modCtrl;
        }
    }

    public static class EventKeyboardTyped extends EventKeyboard {
        public EventKeyboardTyped(EventKeyboard eventKeyboard) {
            super( eventKeyboard );
        }
    }

    public static class EventKeyboardPressed extends EventKeyboard {
        public EventKeyboardPressed(EventKeyboard eventKeyboard) {
            super( eventKeyboard );
        }
    }

    public static class EventKeyboardReleased extends EventKeyboard {
        public EventKeyboardReleased(EventKeyboard eventKeyboard) {
            super( eventKeyboard );
        }
    }



}
