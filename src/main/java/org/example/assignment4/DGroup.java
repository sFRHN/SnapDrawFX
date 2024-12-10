/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

package org.example.assignment4;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class DGroup implements Groupable {

    private final List<Groupable> children;
    private double left, right, top, bottom;


    /**
     * Constructor for DGroup
     * @param items List of Groupable items to be grouped
     */
    public DGroup(List<Groupable> items) {
        children = new ArrayList<Groupable>(items);
        calculateBounds();
    }

    /**
     * Method to check if a point is contained within the group
     */
    public boolean contains(double mx, double my) {
//        return (this.left <= mx && mx <= this.right) &&
//                (this.top <= my && my <= this.bottom);
        for (Groupable child : children) {
            if (child.contains(mx, my)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to move the group by a certain amount
     */
    public void move(double dx, double dy) {
        for (Groupable child : children) {
            child.move(dx, dy);
        }
        calculateBounds();
    }


    /**
     * Rotate the group by a certain amount
     */
    public void rotate(String direction) {
        double centerX = Math.abs(right + left) / 2;
        double centerY = Math.abs(bottom + top) / 2;
        rotate(direction, centerX, centerY);
    }


    /**
     * Rotate the group by a certain amount around a certain point
     */
    public void rotate(String direction, double centerX, double centerY) {
        for (Groupable child : children) {
            child.rotate(direction, centerX, centerY);
        }
        calculateBounds();
    }


    /**
     * Scale the group by a certain amount
     */
    public void scale(String scale) {
        double centerX = Math.abs(right + left) / 2;
        double centerY = Math.abs(bottom + top) / 2;
        scale(scale, centerX, centerY);
    }


    /**
     * Scale the group by a certain amount around a certain point
     */
    public void scale(String scale, double centerX, double centerY) {
        for (Groupable child : children) {
            child.scale(scale, centerX, centerY);
        }
        calculateBounds();
    }


    /**
     * Method to draw the group
     */
    public void draw(GraphicsContext gc, boolean selected) {
        if (selected) {
            gc.setLineWidth(3);
            gc.setStroke(Color.PINK);
            gc.strokeRect(left, top, Math.abs(right-left), Math.abs(bottom-top));
        }
        for (Groupable child : children) {
            child.draw(gc, false);
        }
    }


    /**
     * Bounding box calculation for the group
     */
    private void calculateBounds() {

        double left = Double.MAX_VALUE;
        double right = Double.MIN_VALUE;
        double top = Double.MAX_VALUE;
        double bottom = Double.MIN_VALUE;

        for (Groupable child : children) {
            left = Math.min(left, child.getLeft());
            right = Math.max(right, child.getRight());
            top = Math.min(top, child.getTop());
            bottom = Math.max(bottom, child.getBottom());
        }

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

    }

    // Getters and setters
    public boolean isGroup() { return true; }
    public List<Groupable> getChildren() { return children; }
    public double getLeft() { return this.left; }
    public double getRight() { return this.right; }
    public double getTop() { return this.top; }
    public double getBottom() { return this.bottom; }

}
