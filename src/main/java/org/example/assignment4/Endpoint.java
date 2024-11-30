package org.example.assignment4;

public class Endpoint {

    private double x, y;

    public Endpoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

}
