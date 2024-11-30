package org.example.assignment4;

public class DLine {

    private Endpoint leftEP, rightEP;

    public DLine(double x1, double y1, double x2, double y2) {
        this.leftEP = new Endpoint(x1, y1);
        this.rightEP = new Endpoint(x2, y2);
    }

    public void adjust(double mx, double my) {
        rightEP.setX(mx);
        rightEP.setY(my);
    }

    public double getX1() { return leftEP.getX(); }
    public double getY1() { return leftEP.getY(); }
    public double getX2() { return rightEP.getX(); }
    public double getY2() { return rightEP.getY(); }

    public Endpoint getLeftEndpoint() { return leftEP; }
    public Endpoint getRightEndpoint() { return rightEP; }

}
