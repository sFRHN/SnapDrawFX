/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

package org.example.assignment4;

public class Endpoint {

    private double x, y;


    /**
     * Constructor for Endpoint
     * @param x x-coordinate of the endpoint
     * @param y y-coordinate of the endpoint
     */
    public Endpoint(double x, double y) {
        this.x = x;
        this.y = y;
    }


    // Getters and setters
    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

}
