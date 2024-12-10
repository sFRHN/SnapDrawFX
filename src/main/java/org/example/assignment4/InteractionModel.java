/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

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


    /**
     * Constructor for InteractionModel
     */
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


    /**
     * Adds an item to the selected list
     * @param item
     */
    public void setSelected(Groupable item) {
        if (!selected.contains(item)) {
            selected.add(item);
        }
        notifySubscribers();
    }

    /**
     * Adds an item to the selected list if it is not already there, otherwise removes it
     * @param item
     */
    public void multiSelect(Groupable item) {
        if (selected.contains(item)) {
            selected.remove(item);
        }
        else {
            selected.add(item);
        }
        notifySubscribers();
    }


    /**
     * Clears the selected list
     */
    public void clearSelected() {
        selected.clear();
        notifySubscribers();
    }


    /**
     * Sets the hovered line
     * @param line
     */
    public void setHovered(DLine line) {
        hovered = line;
        notifySubscribers();
    }


    /**
     * Returns the handle that a point is on
     * @param mx
     * @param my
     * @return
     */
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


    /**
     * Checks if a point is on a handle
     * @param mx
     * @param my
     * @return
     */
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


    /**
     * Retuns the rubberband selector
     * @return
     */
    public Rubberband getRubberBand() {
        return rubberband;
    }


    /**
     * Repositions the rubberband selector
     * @param mx
     * @param my
     */
    public void reposRubberband(double mx, double my) {
        rubberband.setX(mx);
        rubberband.setY(my);
        notifySubscribers();
    }


    /**
     * Resizes the rubberband selector
     * @param dx
     * @param dy
     */
    public void resizeRubberband(double dx, double dy) {
        rubberband.setWidth(dx);
        rubberband.setHeight(dy);
        notifySubscribers();
    }


    /**
     * Resets the rubberband selector
     */
    public void resetRubberband() {
        reposRubberband(0,0);
        rubberband.setWidth(0);
        rubberband.setHeight(0);
        notifySubscribers();
    }

}
