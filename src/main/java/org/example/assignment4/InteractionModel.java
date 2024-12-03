package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {

    private DLine hovered;
    private final List<DLine> selected;
    private final List<Subscriber> subscribers;
    private final int handleRadius = 5;

    public InteractionModel() {
        selected = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(Subscriber sub) { subscribers.add(sub);}
    private void notifySubscribers() { subscribers.forEach(Subscriber::modelUpdated); }

    public List<DLine> getSelected() { return selected; }
    public DLine getHovered() { return hovered; }
    public int getRadius() { return handleRadius; }

    public void setSelected(DLine line) {
        selected.clear();
        selected.add(line);
        notifySubscribers();
    }

    public void multiSelect(DLine line) {
        if (selected.contains(line)) {
            selected.remove(line);
        }
        else {
            selected.add(line);
        }
        notifySubscribers();
    }

    public void clearSelected() {
        selected.clear();
        notifySubscribers();
    }

    public void setHovered(DLine line) {
        hovered = line;
        notifySubscribers();
    }


    private Endpoint checkEndPoint(double mx, double my) {
        for (DLine line : selected) {
            if (Math.hypot(mx - line.getX1(), my - line.getY1()) <= handleRadius) {
                return line.getLeftEndpoint();
            }
            else if (Math.hypot(mx - line.getX2(), my - line.getY2()) <= handleRadius) {
                return line.getRightEndpoint();
            }
        }
        return null;
    }


    public boolean onHandle(double mx, double my) {
        return checkEndPoint(mx, my) != null;
    }


    public Endpoint whichEndPoint(double mx, double my) {
        Endpoint ep;

        if ((ep = checkEndPoint(mx, my)) != null) {
            return ep;
        }
        return null;
    }


    public void updatePosition(Endpoint ep, double mx, double my) {
        ep.setX(mx);
        ep.setY(my);
        notifySubscribers();
    }

}
