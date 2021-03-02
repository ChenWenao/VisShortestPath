package com.algoDesign;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

// a AlgoVisHelper class contain some utility functions
public class AlgoVisHelper {
    private AlgoVisHelper() {
    }

    public static void setStrokeWidth(Graphics2D g2d, int w) {
        int strokeWidth = w;
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    public static void setColor(Graphics2D g2d, Color color) {
        g2d.setColor(color);
    }

    public static void strokeCircle(Graphics2D g2d, int x, int y, int r) {

        Ellipse2D circle = new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
        g2d.draw(circle);
    }

    public static void fillCircle(Graphics2D g2d, int x, int y, int r) {

        Ellipse2D circle = new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
        g2d.fill(circle);
    }

    public static void fillRectangle(Graphics2D g2d, int x, int y, int w, int h) {
        Rectangle2D rectangle2D = new Rectangle2D.Double(x, y, w, h);
        g2d.fill(rectangle2D);
    }

    public static void pause(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            System.out.println("Error in Sleeping...");
        }
    }


}
