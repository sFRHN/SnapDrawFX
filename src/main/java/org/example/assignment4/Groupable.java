/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

package org.example.assignment4;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public interface Groupable {
    boolean isGroup();
    List<Groupable> getChildren();
    boolean contains(double mx, double my);
    void draw(GraphicsContext gc, boolean selected);
    void move(double dx, double dy);
    double getLeft();
    double getRight();
    double getTop();
    double getBottom();
    void rotate(String direction);
    void rotate(String direction, double centerX, double centerY);
    void scale(String scale);
    void scale(String scale, double centerX, double centerY);
}
