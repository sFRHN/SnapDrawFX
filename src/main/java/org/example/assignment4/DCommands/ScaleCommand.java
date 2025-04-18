/*
 * NAME: Sayed Farhaan Rafi Bhat
 * NSID: bcl568
 * Student Number: 11354916
 */

package org.example.assignment4.DCommands;

import org.example.assignment4.DCommand;
import org.example.assignment4.Groupable;
import org.example.assignment4.LineModel;

import java.util.ArrayList;
import java.util.List;

public class ScaleCommand implements DCommand {

    private LineModel model;
    private List<Groupable> item;
    private double scaleCount;

    public ScaleCommand(LineModel model, List<Groupable> item, double scaleCount) {
        this.model = model;
        this.item = new ArrayList<Groupable>(item);
        this.scaleCount = scaleCount;
    }

    public void doIt() {

        // Scale the item up if scaleCount is positive
        if (scaleCount > 0) {
            for (int i = 0; i < Math.abs(scaleCount); i++) {
                model.scaleItem(item, "up");
            }
        }

        // Scale the item down if scaleCount is negative
        else if (scaleCount < 0) {
            for (int i = 0; i < Math.abs(scaleCount); i++) {
                model.scaleItem(item, "down");
            }
        }
    }

    public void undo() {

        // Scale the item down if scaleCount is positive
        if (scaleCount > 0) {
            for (int i = 0; i < Math.abs(scaleCount); i++) {
                model.scaleItem(item, "down");
            }
        }

        // Scale the item up if scaleCount is negative
        else if (scaleCount < 0) {
            for (int i = 0; i < Math.abs(scaleCount); i++) {
                model.scaleItem(item, "up");
            }
        }
    }
}
