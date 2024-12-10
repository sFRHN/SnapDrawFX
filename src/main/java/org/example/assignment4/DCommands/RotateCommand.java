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

public class RotateCommand implements DCommand {

    private LineModel model;
    private List<Groupable> item;
    private double rotateCount;

    /**
     * Constructor for RotateCommand
     */
    public RotateCommand(LineModel model, List<Groupable> item, double rotateCount) {
        this.model = model;
        this.item = new ArrayList<Groupable>(item);
        this.rotateCount = rotateCount;
    }

    public void doIt() {
        
        // Rotate the item clockwise if rotateCount is positive
        if (rotateCount > 0) {
            for (int i = 0; i < Math.abs(rotateCount); i++) {
                model.rotateItem(item, "clockwise");
            }
        }

        // Rotate the item counterclockwise if rotateCount is negative
        else if (rotateCount < 0) {
            for (int i = 0; i < Math.abs(rotateCount); i++) {
                model.rotateItem(item, "counterclockwise");
            }
        }
    }

    public void undo() {

        // Rotate the item clockwise if rotateCount is negative
        if (rotateCount > 0) {
            for (int i = 0; i < Math.abs(rotateCount); i++) {
                model.rotateItem(item, "counterclockwise");
            }
        }

        // Rotate the item counterclockwise if rotateCount is positive
        else if (rotateCount < 0) {
            for (int i = 0; i < Math.abs(rotateCount); i++) {
                model.rotateItem(item, "clockwise");
            }
        }
    }


}
