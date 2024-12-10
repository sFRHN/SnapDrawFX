/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class LineModel {

    private final List<Groupable> items;
    private final List<Subscriber> subscribers;


    /**
     * Constructor for LineModel
     */
    public LineModel() {
        items = new ArrayList<>();
        subscribers = new ArrayList<>();
    }


    /**
     * Creates a new line
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public DLine createLine(double x1, double y1, double x2, double y2) {
        return new DLine(x1, y1, x2, y2);
    }


    /**
     * Adds a list of items to the model
     * @param item
     */
    public void addItemList(List<Groupable> item) {
        items.addAll(item);
        System.out.println("Model size: " + items.size());
        notifySubscribers();
    }


    /**
     * Adds an item to the model
     * @param group
     */
    public void addItem(Groupable group) {
        items.add(group);
        System.out.println("Model size: " + items.size());
        notifySubscribers();
    }


    /**
     * Deletes a list of items from the model
     * @param sItems
     */
    public void deleteItemList(List<Groupable> sItems) {
        for (Groupable item : sItems) {
            items.remove(item);
        }
        notifySubscribers();
    }


    /**
     * Deletes an item from the model
     * @param group
     */
    public void deleteItem(Groupable group) {
        items.remove(group);
        notifySubscribers();
    }


    /**
     * Adjusts the line by changing the second endpoint
     * @param line
     * @param x2
     * @param y2
     */
    public void adjustLine(DLine line, double x2, double y2) {
        line.adjust(x2, y2);
        notifySubscribers();
    }


    /**
     * Moves a list of items by a certain amount
     * @param sItems
     * @param dx
     * @param dy
     */
    public void moveItem(List<Groupable> sItems, double dx, double dy) {
        for (Groupable item : sItems ) {
            item.move(dx, dy);
        }
        notifySubscribers();
    }


    /**
     * Updates the position of an endpoint
     * @param ep
     * @param mx
     * @param my
     */
    public void updatePosition(Endpoint ep, double mx, double my) {
        ep.setX(mx);
        ep.setY(my);
        notifySubscribers();
    }


    /**
     * Rotates a list of items
     * @param sItems
     * @param direction
     */
    public void rotateItem(List<Groupable> sItems, String direction) {
        for (Groupable item : sItems) {
            item.rotate(direction);
        }
        notifySubscribers();
    }


    /**
     * Scales a list of items
     * @param sItems
     * @param scale
     */
    public void scaleItem(List<Groupable> sItems, String scale) {
        for (Groupable item : sItems) {
            item.scale(scale);
        }
        notifySubscribers();
    }


    /**
     * Finds the grid point closest to the given point
     * @param sItems
     */
    public Endpoint findGrid(double x, double y) {

        // Snapping to the grid
        x = Math.round(x / 20) * 20;
        y = Math.round(y / 20) * 20;

        return new Endpoint(x, y);
    }


    /**
     * Finds the item that is under the given point
     * @param mx
     * @param my
     * @return
     */
    public Groupable overItem(double mx, double my) {
        for (Groupable item : items) {
            if (item.contains(mx, my)) {
                return item;
            }
        }
        return null;
    }


    /**
     * Returns the list of items in the model
     * @return
     */
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
