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

    public void moveLine(double dx, double dy) {
        this.leftEP.move(dx, dy);
        this.rightEP.move(dx, dy);
    }

    public void updatePosition(Endpoint ep, double mx, double my) {
        if (ep.equals(leftEP)) {
            leftEP.setX(mx);
            leftEP.setY(my);
        }
        else if (ep.equals(rightEP)) {
            rightEP.setX(mx);
            rightEP.setY(my);
        }
    }

    public void rotate(String direction) {

        // Rotation

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


    public boolean onLine(double mx, double my) {

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

}
