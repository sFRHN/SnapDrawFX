package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {

    private DLine selected;
    private List<Subscriber> subscribers;
    private int handleRadius = 5;

    public InteractionModel() {
        selected = null;
        subscribers = new ArrayList<>();
    }

    public DLine getSelected() {
        return selected;
    }

    public void setSelected(DLine line) {
        selected = line;
        notifySubscribers();
    }

    public void clearSelection() {
        selected = null;
        notifySubscribers();
    }

    public int getRadius() {
        return handleRadius;
    }

    public void addSubscriber(Subscriber sub) { subscribers.add(sub);}
    private void notifySubscribers() { subscribers.forEach(Subscriber::modelUpdated); }

}
