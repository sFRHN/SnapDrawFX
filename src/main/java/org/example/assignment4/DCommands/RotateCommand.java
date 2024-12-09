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

    public RotateCommand(LineModel model, List<Groupable> item, double rotateCount) {
        this.model = model;
        this.item = new ArrayList<Groupable>(item);
        this.rotateCount = rotateCount;
    }

    public void doIt() {
        if (rotateCount > 0) {
            for (int i = 0; i < Math.abs(rotateCount); i++) {
                model.rotateItem(item, "clockwise");
            }
        }
        else if (rotateCount < 0) {
            for (int i = 0; i < Math.abs(rotateCount); i++) {
                model.rotateItem(item, "counterclockwise");
            }
        }
    }

    public void undo() {
        if (rotateCount > 0) {
            for (int i = 0; i < Math.abs(rotateCount); i++) {
                model.rotateItem(item, "counterclockwise");
            }
        }
        else if (rotateCount < 0) {
            for (int i = 0; i < Math.abs(rotateCount); i++) {
                model.rotateItem(item, "clockwise");
            }
        }
    }


}
