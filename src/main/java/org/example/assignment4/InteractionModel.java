package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {

    private DLine selected;
    private List<Subscriber> subscribers;


    public InteractionModel() {
        selected = null;
        subscribers = new ArrayList<>();
    }

    public DLine getSelected() {
        return selected;
    }

    public void setSelected(DLine line) {
        selected = line;
    }

    public void clearSelection() {
        selected = null;
    }

    public void addSubscriber(Subscriber sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(Subscriber::modelUpdated);
    }

}
