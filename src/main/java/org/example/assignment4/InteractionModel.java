package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InteractionModel {

    private DLine hovered;
    private final List<Groupable> selected;
    private final List<Subscriber> subscribers;
    private final int handleRadius = 5;
    private final Rubberband rubberband;
    private final Stack<DCommand> undoStack;
    private final Stack<DCommand> redoStack;

    public InteractionModel() {
        selected = new ArrayList<>();
        subscribers = new ArrayList<>();
        rubberband = new Rubberband(0,0,0,0);
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void addSubscriber(Subscriber sub) { subscribers.add(sub);}
    private void notifySubscribers() { subscribers.forEach(Subscriber::modelUpdated); }

    public List<Groupable> getSelected() { return selected; }
    public DLine getHovered() { return hovered; }
    public Stack<DCommand> getUndoStack() { return undoStack; }
    public Stack<DCommand> getRedoStack() { return redoStack; }

    public void setSelected(Groupable item) {
        selected.add(item);
        notifySubscribers();
    }

    public void multiSelect(Groupable item) {
        if (selected.contains(item)) {
            selected.remove(item);
        }
        else {
            selected.add(item);
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
        for (Groupable item : selected) {
            if (!item.isGroup()) {
                DLine line = (DLine) item;
                if (Math.hypot(mx - line.getX1(), my - line.getY1()) <= handleRadius) {
                    return line.getLeftEndpoint();
                }
                else if (Math.hypot(mx - line.getX2(), my - line.getY2()) <= handleRadius) {
                    return line.getRightEndpoint();
                }
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

    public Rubberband getRubberBand() {
        return rubberband;
    }

    public void reposRubberband(double mx, double my) {
        rubberband.setX(mx);
        rubberband.setY(my);
        notifySubscribers();
    }

    public void resizeRubberband(double dx, double dy) {
        rubberband.setWidth(dx);
        rubberband.setHeight(dy);
        notifySubscribers();
    }

    public void resetRubberband() {
        reposRubberband(0,0);
        rubberband.setWidth(0);
        rubberband.setHeight(0);
        notifySubscribers();
    }

}
