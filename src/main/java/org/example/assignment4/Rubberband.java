package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class Rubberband {

    private double x, y, width, height;

    public Rubberband(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(double mx, double my) {
        return (this.x <= mx && mx <= this.x + this.width) &&
                (this.y <= my && my <= this.y + this.height);
    }

    public List<Groupable> getItemsWithin(List<Groupable> items) {

        List<Groupable> itemsWithin = new ArrayList<Groupable>();

        for (Groupable item : items) {
            if (this.contains(item.getLeft(), item.getTop()) &&
                this.contains(item.getRight(), item.getTop()) &&
                this.contains(item.getLeft(), item.getBottom()) &&
                this.contains(item.getRight(), item.getBottom()) ) {

                itemsWithin.add(item);
                System.out.println("Added");
            }
        }

        return itemsWithin;

    }


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
