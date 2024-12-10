/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class Rubberband {

    private double x, y, width, height;


    /**
     * Constructor for Rubberband
     * @param x x-coordinate of the rubberband
     * @param y y-coordinate of the rubberband
     * @param width width of the rubberband
     * @param height height of the rubberband
     */
    public Rubberband(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    /**
     * Checks if the rubberband contains a point
     * @param mx x-coordinate of the point
     * @param my y-coordinate of the point
     * @return true if the rubberband contains the point, false otherwise
     */
    public boolean contains(double mx, double my) {
        return (this.x <= mx && mx <= this.x + this.width) &&
                (this.y <= my && my <= this.y + this.height);
    }


    /**
     * Gets the items within the rubberband
     * @param items List of items to check
     * @return List of items within the rubberband
     */
    public List<Groupable> getItemsWithin(List<Groupable> items) {

        List<Groupable> itemsWithin = new ArrayList<Groupable>();

        for (Groupable item : items) {
            if (this.contains(item.getLeft(), item.getTop()) &&
                this.contains(item.getRight(), item.getTop()) &&
                this.contains(item.getLeft(), item.getBottom()) &&
                this.contains(item.getRight(), item.getBottom()) ) {

                itemsWithin.add(item);
            }
        }

        return itemsWithin;

    }


    // Getters and setters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }

    public void addWidth(double w) { this.width += w; }
    public void addHeight(double h) { this.height += h; }




}
