package org.example.assignment4;

public class DLine {

    private double x1, x2, y1, y2;

    public DLine(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void adjust(double dx, double dy) {
        x2 = dx;
        y2 = dy;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public boolean checkEndPoint(double x, double y) {
        return Math.hypot(x - x1, y - y1) < 20 || Math.hypot(x - x2, y - y2) < 20;
    }

    public Endpoint whichEndPoint(double x, double y) {
        if (Math.hypot(x - x1, y - y1) < 20) {
            return new Endpoint(x1, y1);
        } else {
            return new Endpoint(x2, y2);
        }
    }

}
