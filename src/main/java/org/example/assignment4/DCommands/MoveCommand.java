package org.example.assignment4.DCommands;

import org.example.assignment4.DCommand;
import org.example.assignment4.DLine;
import org.example.assignment4.Groupable;
import org.example.assignment4.LineModel;

import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements DCommand {

    private LineModel model;
    private List<Groupable> item;
    private double startX, startY, endX, endY;

    public MoveCommand(LineModel model, List<Groupable> item, double startX, double startY, double endX, double endY) {
        this.model = model;
        this.item = new ArrayList<Groupable>(item);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void doIt() {
        model.moveItem(item, endX - startX , endY - startY);
    }

    public void undo() {
        model.moveItem(item, startX - endX, startY - endY);
    }
}
