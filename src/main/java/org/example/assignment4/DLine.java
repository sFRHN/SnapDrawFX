/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

package org.example.assignment4;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class DLine implements Groupable {

    private final Endpoint leftEP, rightEP;


    /**
     * Constructor for DLine
     */
    public DLine(double x1, double y1, double x2, double y2) {
        this.leftEP = new Endpoint(x1, y1);
        this.rightEP = new Endpoint(x2, y2);
    }


    /**
     * Method to adjust the line by the endpoint
     */
    public void adjust(double mx, double my) {
        rightEP.setX(mx);
        rightEP.setY(my);
    }


    /**
     * Method to move the line by a certain amount
     */
    public void move(double dx, double dy) {
        this.leftEP.move(dx, dy);
        this.rightEP.move(dx, dy);
    }


    /**
     * Method to rotate the line by a certain amount
     */
    public void rotate(String direction) {

        double angle = Math.toRadians(0);
        if (direction.equals("clockwise")) {
            angle = Math.toRadians(5);
        } else if (direction.equals("counterclockwise")) {
            angle = Math.toRadians(-5);
        }
        double midX = (getX1() + getX2()) / 2;
        double midY = (getY1() + getY2()) / 2;

        double newX1 = midX + (getX1() - midX) * Math.cos(angle) - (getY1() - midY) * Math.sin(angle);
        double newY1 = midY + (getX1() - midX) * Math.sin(angle) + (getY1() - midY) * Math.cos(angle);
        double newX2 = midX + (getX2() - midX) * Math.cos(angle) - (getY2() - midY) * Math.sin(angle);
        double newY2 = midY + (getX2() - midX) * Math.sin(angle) + (getY2() - midY) * Math.cos(angle);

        leftEP.setX(newX1);
        leftEP.setY(newY1);
        rightEP.setX(newX2);
        rightEP.setY(newY2);

    }


    /**
     * Method to rotate the line by a certain amount around a certain point
     */
    public void rotate(String direction, double centerX, double centerY) {

        double angle = Math.toRadians(0);
        if (direction.equals("clockwise")) {
            angle = Math.toRadians(5);
        } else if (direction.equals("counterclockwise")) {
            angle = Math.toRadians(-5);
        }

        double newX1 = centerX + (getX1() - centerX) * Math.cos(angle) - (getY1() - centerY) * Math.sin(angle);
        double newY1 = centerY + (getX1() - centerX) * Math.sin(angle) + (getY1() - centerY) * Math.cos(angle);
        double newX2 = centerX + (getX2() - centerX) * Math.cos(angle) - (getY2() - centerY) * Math.sin(angle);
        double newY2 = centerY + (getX2() - centerX) * Math.sin(angle) + (getY2() - centerY) * Math.cos(angle);

        leftEP.setX(newX1);
        leftEP.setY(newY1);
        rightEP.setX(newX2);
        rightEP.setY(newY2);

    }


    /**
     * Method to scale the line by a certain amount
     */
    public void scale(String scale) {

        double factor = 1.0;
        if (scale.equals("up")) {
            factor = 1.1;
        }
        else if (scale.equals("down")) {
            factor = 0.9;
        }
        double midX = (getX1() + getX2()) / 2;
        double midY = (getY1() + getY2()) / 2;

        double newX1 = midX + (getX1() - midX) * factor;
        double newY1 = midY + (getY1() - midY) * factor;
        double newX2 = midX + (getX2() - midX) * factor;
        double newY2 = midY + (getY2() - midY) * factor;

        leftEP.setX(newX1);
        leftEP.setY(newY1);
        rightEP.setX(newX2);
        rightEP.setY(newY2);

    }


    /**
     * Method to scale the line by a certain amount around a certain point
     */
    public void scale(String scale, double centerX, double centerY) {

        double factor = 1.0;
        if (scale.equals("up")) {
            factor = 1.1;
        }
        else if (scale.equals("down")) {
            factor = 0.9;
        }

        double newX1 = centerX + (getX1() - centerX) * factor;
        double newY1 = centerY + (getY1() - centerY) * factor;
        double newX2 = centerX + (getX2() - centerX) * factor;
        double newY2 = centerY + (getY2() - centerY) * factor;

        leftEP.setX(newX1);
        leftEP.setY(newY1);
        rightEP.setX(newX2);
        rightEP.setY(newY2);

    }


    /**
     * Checks if the line contains a point
     */
    public boolean contains(double mx, double my) {

        double x1 = getX1();
        double y1 = getY1();
        double x2 = getX2();
        double y2 = getY2();

        double length = Math.hypot(x2 - x1, y2 - y1);
        double ratioA = (y1 - y2) / length;
        double ratioB = (x2 - x1) / length;
        double ratioC = -1 * ((y1 - y2) * x1 + (x2 - x1) * y1) / length;

        double distance = Math.abs(ratioA * mx + ratioB * my + ratioC);

        double t = ((mx-x1)*(x2-x1) + (my-y1)*(y2-y1)) / (length * length);

        if (t< 0 || t > 1) {
            return false;
        }

        return distance <= 5;

    }


    /**
     * Draws the line
     */
    public void draw(GraphicsContext gc, boolean selected) {

        gc.setLineWidth(2);
        gc.setStroke(selected ? Color.PINK : Color.PURPLE);
        gc.strokeLine(getX1(), getY1(), getX2(), getY2());
        if (selected) { drawHandles(gc); }

    }


    /**
     * Method to draw the handles
     */
    private void drawHandles(GraphicsContext gc) {

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        double circleRadius = 5;
        gc.strokeOval(this.getX1()- circleRadius, this.getY1() - circleRadius, 2 * circleRadius, 2 * circleRadius);
        gc.strokeOval(this.getX2() - circleRadius, this.getY2() - circleRadius, 2 * circleRadius, 2 * circleRadius);
        gc.setFill(Color.WHITESMOKE);
        gc.fillOval(this.getX1()- circleRadius, this.getY1() - circleRadius, 2 * circleRadius, 2 * circleRadius);
        gc.fillOval(this.getX2() - circleRadius, this.getY2() - circleRadius, 2 * circleRadius, 2 * circleRadius);

    }


    public double getX1() { return leftEP.getX(); }
    public double getY1() { return leftEP.getY(); }
    public double getX2() { return rightEP.getX(); }
    public double getY2() { return rightEP.getY(); }
    public Endpoint getLeftEndpoint() { return leftEP; }
    public Endpoint getRightEndpoint() { return rightEP; }
    public boolean isGroup() { return false; }
    public List<Groupable> getChildren() { return null; }

    public double getLeft() { return Math.min(leftEP.getX(), rightEP.getX()); }
    public double getRight() { return Math.max(rightEP.getX(), leftEP.getX()); }
    public double getTop() { return Math.min(leftEP.getY(), rightEP.getY()); }
    public double getBottom() { return Math.max(rightEP.getY(), leftEP.getY()); }

}
