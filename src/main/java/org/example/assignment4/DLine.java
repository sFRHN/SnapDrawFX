package org.example.assignment4;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class DLine implements Groupable {

    private final Endpoint leftEP, rightEP;

    public DLine(double x1, double y1, double x2, double y2) {
        this.leftEP = new Endpoint(x1, y1);
        this.rightEP = new Endpoint(x2, y2);
    }

    public boolean isGroup() {
        return false;
    }

    public List<Groupable> getChildren() {
        return null;
    }

    public void adjust(double mx, double my) {
        rightEP.setX(mx);
        rightEP.setY(my);
    }


    public void move(double dx, double dy) {
        this.leftEP.move(dx, dy);
        this.rightEP.move(dx, dy);
    }


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


    public boolean contains(double mx, double my) {

        double numerator, denominator, fraction;
        numerator = Math.abs((getY2() - getY1()) * mx - (getX2() - getX1()) * my + getX2() * getY1() - getY2() * getX1());
        denominator = Math.sqrt(Math.pow(getY2() - getY1(), 2) + Math.pow(getX2() - getX1(), 2));
        fraction = numerator / denominator;

        if (fraction > 5) {
            return false;
        }

        // Check if the point is within the segment
        double minX = Math.min(getX1(), getX2());
        double maxX = Math.max(getX1(), getX2());
        double minY = Math.min(getY1(), getY2());
        double maxY = Math.max(getY1(), getY2());

        return (mx >= minX && mx <= maxX && my >= minY && my <= maxY);

    }

    public void draw(GraphicsContext gc, boolean selected) {

        gc.setLineWidth(2);
        gc.setStroke(selected ? Color.PINK : Color.MEDIUMPURPLE);
        gc.strokeLine(getX1(), getY1(), getX2(), getY2());
        if (selected) { drawHandles(gc); }

    }

    private void drawHandles(GraphicsContext gc) {

        gc.setFill(Color.WHITE);
        gc.setLineWidth(2);
        double circleRadius = 5;
        gc.strokeOval(this.getX1()- circleRadius, this.getY1() - circleRadius, 2 * circleRadius, 2 * circleRadius);
        gc.strokeOval(this.getX2() - circleRadius, this.getY2() - circleRadius, 2 * circleRadius, 2 * circleRadius);
        gc.fillOval(this.getX1()- circleRadius, this.getY1() - circleRadius, 2 * circleRadius, 2 * circleRadius);
        gc.fillOval(this.getX2() - circleRadius, this.getY2() - circleRadius, 2 * circleRadius, 2 * circleRadius);

    }


    public double getX1() { return leftEP.getX(); }
    public double getY1() { return leftEP.getY(); }
    public double getX2() { return rightEP.getX(); }
    public double getY2() { return rightEP.getY(); }
    public Endpoint getLeftEndpoint() { return leftEP; }
    public Endpoint getRightEndpoint() { return rightEP; }

    public double getLeft() { return Math.min(leftEP.getX(), rightEP.getX()); }
    public double getRight() { return Math.max(rightEP.getX(), leftEP.getX()); }
    public double getTop() { return Math.min(leftEP.getY(), rightEP.getY()); }
    public double getBottom() { return Math.max(rightEP.getY(), leftEP.getY()); }

}
