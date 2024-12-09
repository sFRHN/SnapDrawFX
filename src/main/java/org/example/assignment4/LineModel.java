package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class LineModel {

    private final List<Groupable> items;
    private final List<Subscriber> subscribers;

    public LineModel() {
        items = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public DLine createLine(double x1, double y1, double x2, double y2) {
        DLine line = new DLine(x1, y1, x2, y2);
        items.add(line);
        notifySubscribers();
        return line;
    }

    public void addItem(List<Groupable> item) {
        items.addAll(item);
        notifySubscribers();
    }

    public void addGroup(Groupable group) {
        items.add(group);
        notifySubscribers();
    }

    public void deleteItem(List<Groupable> sItems) {
        for (Groupable item : sItems) {
            items.remove(item);
        }
        notifySubscribers();
    }

    public void deleteGroup(Groupable group) {
        items.remove(group);
        notifySubscribers();
    }

    public void adjustLine(DLine line, double x2, double y2) {
        line.adjust(x2, y2);
        notifySubscribers();
    }

    public void moveItem(List<Groupable> sItems, double dx, double dy) {
        for (Groupable item : sItems ) {
            item.move(dx, dy);
        }
        notifySubscribers();
    }

    public void rotateItem(List<Groupable> sItems, String direction) {
        for (Groupable item : sItems) {
            item.rotate(direction);
        }
        notifySubscribers();
    }

    public void scaleItem(List<Groupable> sItems, String scale) {
        for (Groupable item : sItems) {
            item.scale(scale);
        }
        notifySubscribers();
    }

    public Endpoint findGrid(double x, double y) {

        // Snapping to the grid
        x = Math.round(x / 20) * 20;
        y = Math.round(y / 20) * 20;

        return new Endpoint(x, y);
    }

    public Groupable overItem(double mx, double my) {
        for (Groupable item : items) {
            if (item.contains(mx, my)) {
                return item;
            }
        }
        return null;
    }

    public List<Groupable> getItems() {
        return items;
    }

    public void addSubscriber(Subscriber sub) {
        subscribers.add(sub);
        notifySubscribers();
    }

    private void notifySubscribers() {
        subscribers.forEach(Subscriber::modelUpdated);
    }

}
