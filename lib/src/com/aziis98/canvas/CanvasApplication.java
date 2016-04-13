package com.aziis98.canvas;

import com.aziis98.input.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class CanvasApplication {

    public  JFrame       frame;
    private FrameCanvas  canvas;
    private CanvasApp canvasApp;

    private CanvasApplication(CanvasApp canvasApp) {

        frame = new JFrame( "Untitled" );
        frame.setSize( 1200, 900 );
        frame.setLocationRelativeTo( null );
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

        System.setProperty( "sun.awt.noerasebackground", "true" );

        canvas = new FrameCanvas();
        this.canvasApp = canvasApp;
        this.canvasApp.setup( frame, canvas );

        Mouse.addListeners( canvas );
        Keyboard.addListeners( canvas );

        frame.addComponentListener( new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                BufferedImage newImage = new BufferedImage( canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB );
                newImage.getGraphics().drawImage( canvas.image, 0, 0, null );
                canvas.image = newImage;
                canvas.repaint();
            }
        } );

        frame.getContentPane().add( canvas );
        frame.setVisible( true );
    }

    public static void launch(CanvasApp canvasApp) {
        SwingUtilities.invokeLater( () -> new CanvasApplication(canvasApp) );
    }

    private class FrameCanvas extends Canvas {
        public BufferedImage image = new BufferedImage( frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB );

        @Override
        public void update(Graphics g) {
            this.paint( g );
        }

        @Override
        public void paint(Graphics g) {

            Graphics2D g2 = (Graphics2D) image.getGraphics();

            g2.setBackground( Color.WHITE );
            g2.clearRect( 0, 0, frame.getWidth(), frame.getHeight() );

            canvasApp.update();
            canvasApp.render( g2 );

            g.drawImage( image, 0, 0, null );

            Mouse.reset();
            Keyboard.reset();

            repaint();
        }

    }

}
