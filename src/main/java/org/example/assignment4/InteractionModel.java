package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {

    private DLine selected;
    private DLine hovered;
    private final List<Subscriber> subscribers;
    private final int handleRadius = 5;

    public InteractionModel() {
        selected = null;
        subscribers = new ArrayList<>();
    }

    public DLine getSelected() { return selected; }
    public void setSelected(DLine line) {
        selected = line;
        notifySubscribers();
    }
    public void clearSelected() {
        selected = null;
        notifySubscribers();
    }

    public DLine getHovered() { return hovered; }
    public void setHovered(DLine line) {
        hovered = line;
        notifySubscribers();
    }
    public void clearHovered() {
        hovered = null;
        notifySubscribers();
    }

    public int getRadius() { return handleRadius; }
    public void addSubscriber(Subscriber sub) { subscribers.add(sub);}
    private void notifySubscribers() { subscribers.forEach(Subscriber::modelUpdated); }


    private boolean checkLeftPoint(double mx, double my) {
        return Math.hypot(mx - selected.getX1(), my - selected.getY1()) <= handleRadius;
    }


    private boolean checkRightPoint(double mx, double my) {
        return Math.hypot(mx - selected.getX2(), my - selected.getY2()) <= handleRadius;
    }


    public boolean onHandle(double mx, double my) {
        return checkLeftPoint(mx, my) || checkRightPoint(mx, my);
    }


    public Endpoint whichEndPoint(double mx, double my) {
        if (checkLeftPoint(mx, my)) {
            return selected.getLeftEndpoint();
        }
        else if (checkRightPoint(mx, my)) {
            return selected.getRightEndpoint();
        }
        else {
            return null;
        }
    }

    public void updatePosition(Endpoint ep, double mx, double my) {
        selected.updatePosition(ep, mx, my);
        notifySubscribers();
    }

}
